package com.marketplace.framework.mongo.annotation.processor;

import com.squareup.javapoet.*;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;

import static java.lang.String.format;

public class CodecProviderBuilder extends BaseCodecBuilder{
    private final Filer filer;
    private final ClassName className;
    private final TypeName typeName;

    public CodecProviderBuilder(Filer filer, Element element) {
        this.filer = filer;
        this.className = getClassName(element.asType());
        typeName = TypeName.get(element.asType());
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
                                .addMember("value", "$S", "unchecked").build())
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
}
