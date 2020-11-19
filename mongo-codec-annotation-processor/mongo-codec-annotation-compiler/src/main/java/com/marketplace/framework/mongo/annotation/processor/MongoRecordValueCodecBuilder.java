package com.marketplace.framework.mongo.annotation.processor;

import com.squareup.javapoet.*;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// https://www.howtobuildsoftware.com/index.php/how-do/oqS/java-mongodb-encoding-bson-mongodb-bson-codec-not-being-used-while-encoding-object
// https://www.edureka.co/blog/mongodb-client/
public class MongoRecordValueCodecBuilder {

    private final Filer filer;
    private final TypeMirror elementTypeMirror;
    private final ClassName className;
    private final TypeName elementTypeName;
    private final List<? extends Element> recordComponents;
    private final Messager messager;

    public MongoRecordValueCodecBuilder(Filer filer, Element element, Messager messager) {
        this.filer = filer;
        this.elementTypeMirror = element.asType();
        this.className = getClassName(this.elementTypeMirror);
        this.elementTypeName = TypeName.get(elementTypeMirror);
        this.messager = messager;
        this.recordComponents = element.getEnclosedElements()
                .stream()
                .filter(component -> component.getKind()
                        .equals(ElementKind.RECORD_COMPONENT))
                .collect(Collectors.toList());
    }


    private ClassName getClassName(TypeMirror typeMirror) {
        String rawString = typeMirror.toString();
        int dotPosition = rawString.lastIndexOf(".");
        String packageName = rawString.substring(0, dotPosition);
        String className = rawString.substring(dotPosition + 1);
        return ClassName.get(packageName, className);
    }

    public void generateCodec() throws IOException {
        ClassName codec = ClassName.get("org.bson.codecs", "Codec");
        ParameterizedTypeName parameterizedTypeName =
                ParameterizedTypeName.get(codec, elementTypeName);

        TypeSpec codecTypeSpec = TypeSpec.classBuilder(this.className.simpleName() + "Codec")
                .addSuperinterface(parameterizedTypeName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(FieldSpec
                        .builder(CodecRegistry.class, "codecRegistry", Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addField(FieldSpec
                        .builder(DocumentCodec.class, "documentCodec", Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addMethod(generateConstructor())
                .addMethod(generateDecodeMethod())
                .addMethod(generateEncodeMethod())
                .addMethod(generateGetEncoderClass())
                .build();

        JavaFile javaFile = JavaFile.builder(this.className.packageName(), codecTypeSpec)
                .build();

        javaFile.writeTo(filer);
    }

    private MethodSpec generateConstructor() {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(CodecRegistry.class, "codecRegistry")
                .addParameter(DocumentCodec.class, "documentCodec");
        builder.addStatement("this.codecRegistry = codecRegistry");
        builder.addStatement("this.documentCodec = documentCodec");
        return builder.build();
    }

    private MethodSpec generateGetEncoderClass() {
        ClassName clzz = ClassName.get(Class.class);
        ParameterizedTypeName parameterizedTypeName =
                ParameterizedTypeName.get(clzz, elementTypeName);

        MethodSpec.Builder builder = MethodSpec.methodBuilder("getEncoderClass")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(parameterizedTypeName)
                .addStatement("return $T.class", elementTypeName);

        return builder.build();
    }

    private MethodSpec generateEncodeMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("encode")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(BsonWriter.class, "writer")
                .addParameter(elementTypeName, "value")
                .addParameter(EncoderContext.class, "encoderContext");

        var documentTypeName = TypeName.get(Document.class);
        CodeBlock.Builder documentCodecBuilder = CodeBlock
                .builder()
                .addStatement("var doc = new $T()", documentTypeName);

        for (Element recordComponent : recordComponents) {
            Name simpleName = recordComponent.getSimpleName();
            documentCodecBuilder.addStatement("doc.put($S, value.$L())", simpleName, simpleName);
        }

//    documentCodecBuilder.addStatement("documentCodec.encode(writer, doc, encoderContext)")
//        .build();

        builder.addCode(documentCodecBuilder.build());

        return builder.build();
    }

    private MethodSpec generateDecodeMethod() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("decode")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(elementTypeName)
                .addParameter(BsonReader.class, "reader")
                .addParameter(DecoderContext.class, "decoderContext");
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        codeBlockBuilder.addStatement("var doc = documentCodec.decode(reader, decoderContext)");
//        codeBlockBuilder.addStatement("reader.readStartDocument()");

        List<Name> constructorValues = new ArrayList<>();
        for (Element recordComponent : recordComponents) {
//                    TypeMirror recordComponentTypeMirror = recordComponent.asType();
            Name simpleName = recordComponent.getSimpleName();
            TypeName componentTypeName = TypeName.get(recordComponent.asType());
            messager.printMessage(Diagnostic.Kind.NOTE, String.format("Record Type: %s, Simple Name: %s", componentTypeName, simpleName));
            if (TypeName.get(String.class).equals(componentTypeName)) {
                codeBlockBuilder.addStatement("$T $L = doc.getString($S)", componentTypeName, simpleName, simpleName);
            } else if (componentTypeName.isPrimitive()) {
                String primitiveTypeName = componentTypeName.toString();
                String methodSuffix = capitalize(primitiveTypeName);
                if (primitiveTypeName.equals("int")) {
                    methodSuffix = "Integer";
                }
                codeBlockBuilder.addStatement("$L $L = doc.get$L($S)", primitiveTypeName, simpleName, methodSuffix, simpleName);
            } else if (TypeName.get(Integer.class).equals(componentTypeName)) {
                codeBlockBuilder.addStatement("$T $L = doc.getInteger($S)", componentTypeName, simpleName, simpleName);
            } else if (TypeName.get(Long.class).equals(componentTypeName)) {
                codeBlockBuilder.addStatement("$T $L = doc.getLong($S)", componentTypeName, simpleName, simpleName);
            } else if (TypeName.get(Double.class).equals(componentTypeName)) {
                codeBlockBuilder.addStatement("$T $L = doc.getDouble($S)", componentTypeName, simpleName, simpleName);
            } else if (TypeName.get(Boolean.class).equals(componentTypeName)) {
                codeBlockBuilder.addStatement("$T $L = doc.getBoolean($S)", componentTypeName, simpleName, simpleName);
            } else if (TypeName.get(Float.class).equals(componentTypeName)) {
                codeBlockBuilder.addStatement("$T $L = doc.getFloat($S)", componentTypeName, simpleName, simpleName);
            } else {
//                Codec<LocalDate> dateCodec = codecRegistry.get(LocalDate.class);
            }
            constructorValues.add(simpleName);

        }
//
        String constructorVariables = constructorValues.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        codeBlockBuilder.addStatement("return new $T($L)", elementTypeName, constructorVariables);
//
//    builder.addCode(documentCodecBuilder.build());
//        codeBlockBuilder.addStatement("reader.readEndDocument()");
        builder.addCode(codeBlockBuilder.build())
                .addStatement("return null");
        return builder.build();
    }

    private boolean checkClassType() {
        List<Class<?>> classes = List.of(
                String.class,
                Integer.class,
                Long.class,
                Short.class,
                Character.class,
                Boolean.class,
                Float.class,
                Double.class
        );
        return false;
    }

    String capitalize(String value) {
        char[] chars = value.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    public void generateCodecProvider() {

    }
}
