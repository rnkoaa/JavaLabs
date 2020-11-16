package com.marketplace.framework.mongo.annotation.processor;

import com.squareup.javapoet.*;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.Code;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.ElementFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class CodecBuilder {

    private final Filer filer;
    private final ClassName className;
    private final TypeName typeName;
    private final TypeMirror classTypeMirror;
    private final Element element;

    CodecBuilder(Filer filer, Element element, ClassName className) {
        this.filer = filer;
        this.className = className;
        this.element = element;
        this.classTypeMirror = element.asType();
        typeName = TypeName.get(classTypeMirror);
    }

    public void generate() throws IOException {

        ClassName codec = ClassName.get("org.bson.codecs", "Codec");
        ParameterizedTypeName parameterizedTypeName =
                ParameterizedTypeName.get(codec, typeName);

        TypeSpec helloWorld = TypeSpec.classBuilder(this.className.simpleName() + "Codec")
                .addSuperinterface(parameterizedTypeName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(generateDecodeMethod())
                .addMethod(generateEncodeMethod())
                .addMethod(generateGetEncoderClass())
                .build();
        JavaFile javaFile = JavaFile.builder(this.className.packageName(), helloWorld)
                .build();

        javaFile.writeTo(filer);
    }

    private MethodSpec generateDecodeMethod() {

        List<ExecutableElement> executableElements = ElementFilter.constructorsIn(element.getEnclosedElements());

        Optional<ExecutableElement> any = executableElements.stream()
                .filter(it -> it.getParameters().size() == 1)
                .findAny();

        Optional<? extends VariableElement> variableElement = any.map(it -> it.getParameters().get(0));

        MethodSpec.Builder builder = MethodSpec.methodBuilder("decode")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(typeName)
                .addParameter(BsonReader.class, "reader")
                .addParameter(DecoderContext.class, "decoderContext");

        variableElement.ifPresentOrElse(parameter -> {
            TypeMirror typeMirror = parameter.asType();
            builder.addStatement("String value = reader.readString()");
            builder.beginControlFlow("if(value == null || value.isEmpty())")
                    .addStatement("return null")
                    .endControlFlow();
            if (typeMirror.toString().equals("java.lang.String")) {
                builder.addStatement("return new $T(value)", typeName);
            } else if (typeMirror.toString().equals("java.util.UUID")) {
                builder.addStatement("return new $T($T.fromString(value))", typeName, UUID.class);
            }

        }, () -> {
            builder.addStatement("return null");
        });

        return builder.build();
    }

    private MethodSpec generateEncodeMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("encode")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(BsonWriter.class, "writer")
                .addParameter(typeName, "value")
                .addParameter(EncoderContext.class, "encoderContext")
                .addStatement("writer.writeString(value.toString())");

        return builder.build();
    }

    private MethodSpec generateGetEncoderClass() {
        ClassName clzz = ClassName.get(Class.class);
        ParameterizedTypeName parameterizedTypeName =
                ParameterizedTypeName.get(clzz, typeName);

        MethodSpec.Builder builder = MethodSpec.methodBuilder("getEncoderClass")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(parameterizedTypeName)
                .addStatement("return $T.class", typeName);

        return builder.build();
    }

    public void generateCodecProvider() throws IOException {
        TypeVariableName tTypeVariable = TypeVariableName.get("T");
        TypeName codecProviderTypeName = TypeName.get(CodecProvider.class);
        ClassName codecProviderClassName = ClassName.get(Codec.class);
        ParameterizedTypeName returnTypeName = ParameterizedTypeName.get(codecProviderClassName, tTypeVariable);
        ClassName clzz = ClassName.get(Class.class);
        ParameterizedTypeName parameterizedTypeName =
                ParameterizedTypeName.get(clzz, tTypeVariable);

        TypeSpec.Builder coderProviderTypeBuilder = TypeSpec.classBuilder(format("%sCodecProvider", this.className.simpleName()))
                .addSuperinterface(codecProviderTypeName)
                .addModifiers(Modifier.PUBLIC);

        String parameterizedTypeVariable = "clzz";

        MethodSpec.Builder getBuilder =
                MethodSpec.methodBuilder("get")
                        .addTypeVariable(tTypeVariable)
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                                .addMember("value", "$S", "unchecked" ).build())
                        .addParameter(parameterizedTypeName, parameterizedTypeVariable)
                        .addParameter(CodecRegistry.class, "codecRegistry")
                        .returns(returnTypeName);

        getBuilder.beginControlFlow("if ($L != $T.class)", parameterizedTypeVariable, typeName)
                .addStatement("return null")
                .endControlFlow()
                .addStatement("return (Codec<T>) new $TCodec()", typeName);

        coderProviderTypeBuilder.addMethod(getBuilder.build());


        JavaFile javaFile = JavaFile
                .builder(this.className.packageName(), coderProviderTypeBuilder.build())
                .build();

        javaFile.writeTo(filer);
    }

    /*
        import com.marketplace.domain.classifiedad.ClassifiedAdId;
        import org.bson.codecs.Codec;
        import org.bson.codecs.configuration.CodecProvider;
        import org.bson.codecs.configuration.CodecRegistry;

        public class ClassifiedAdIdCodecProvider implements CodecProvider {
            @Override
            @SuppressWarnings("unchecked")
            public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
                if (clazz == ClassifiedAdId.class) {
                    return (Codec<T>) new ClassifiedAdIdCodec();
                }

                return null;
            }
        }



public class ClassifiedAdIdCodec implements Codec<ClassifiedAdId> {
    @Override
    public ClassifiedAdId decode(BsonReader reader, DecoderContext decoderContext) {
        return ClassifiedAdId.fromString(reader.readString());
    }

    @Override
    public void encode(BsonWriter writer, ClassifiedAdId value, EncoderContext encoderContext) {
        writer.writeString(value.toString());
    }

    @Override
    public Class<ClassifiedAdId> getEncoderClass() {
        return ClassifiedAdId.class;
    }
}
     */
}
