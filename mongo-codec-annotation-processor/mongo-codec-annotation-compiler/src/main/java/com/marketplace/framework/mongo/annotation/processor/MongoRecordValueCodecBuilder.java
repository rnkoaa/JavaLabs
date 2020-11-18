package com.marketplace.framework.mongo.annotation.processor;

import com.squareup.javapoet.*;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;

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
                .addParameter(DocumentCodec.class, "documentCodec");
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
//            TypeMirror recordComponentTypeMirror = recordComponent.asType();
            Name simpleName = recordComponent.getSimpleName();
//            if (TypeName.get(String.class).equals(TypeName.get(recordComponentTypeMirror))) {
//                messager.printMessage(Diagnostic.Kind.NOTE, "Types match");
//            } else {
//                messager.printMessage(Diagnostic.Kind.NOTE, "Types do not match");
//            }
            documentCodecBuilder.addStatement("doc.put($S, value.$L())", simpleName, simpleName);
        }

        documentCodecBuilder.addStatement("documentCodec.encode(writer, doc, encoderContext)")
                .build();

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

        // Document document = documentCodec.decode(reader, decoderContext);
        //Fruit fruit = new Fruit();
        //if (document.getString("id") != null) {
        //fruit.setId(document.getString("id"));
        //}
        //fruit.setName(document.getString("name"));
        //fruit.setDescription(document.getString("description"));
        //return fruit;

        CodeBlock.Builder documentCodecBuilder = CodeBlock
                .builder()
                .addStatement("var doc = documentCodec.decode(reader, decoderContext)");

        List<Name> constructorValues = new ArrayList<>();
        for (Element recordComponent : recordComponents) {
//                    TypeMirror recordComponentTypeMirror = recordComponent.asType();
            Name simpleName = recordComponent.getSimpleName();
            TypeName componentTypeName = TypeName.get(recordComponent.asType());
            if (TypeName.get(String.class).equals(componentTypeName)) {
                documentCodecBuilder.addStatement("$T $L = doc.getString($S)", componentTypeName, simpleName, simpleName);
            }
            constructorValues.add(simpleName);

            ////            if (TypeName.get(String.class).equals(TypeName.get(recordComponentTypeMirror))) {
            ////                messager.printMessage(Diagnostic.Kind.NOTE, "Types match");
            ////            } else {
            ////                messager.printMessage(Diagnostic.Kind.NOTE, "Types do not match");
            ////            }
            //            documentCodecBuilder.addStatement("doc.put($S, value.$L())", simpleName.toString(), simpleName);
            //        }
        }

        /*
         private void renderBuildMethod(Element record, List<Element> recordComponents, PrintWriter out) {
        out.println("    public %s build() {".formatted(record.getSimpleName()));
        out.print("        return new %s(".formatted(record.getSimpleName()));
        out.print(recordComponents.stream()
                .map(o -> o.getSimpleName())
                .collect(Collectors.joining(", ")));
        out.println(");");
        out.println("    }");
        out.println();
    }

         */

//        documentCodecBuilder.addStatement("return new $T()", elementTypeName, elementTypeName, elementTypeName);

        String constructorVariables = constructorValues.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        builder.addCode(documentCodecBuilder.build());
        builder.addStatement("return null");
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


    public void generateCodecProvider() {

    }
}
