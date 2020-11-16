package com.marketplace.framework.mongo.annotation.processor;

import com.google.auto.service.AutoService;
import com.marketplace.framework.mongo.annotations.MongoStringCodec;
import com.squareup.javapoet.ClassName;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


//@SupportedAnnotationTypes(
//        "com.marketplace.framework.mongo.annotations.MongoStringCodec"
//)
@AutoService(Processor.class)
public class MongoStringCodecProcessor extends AbstractProcessor {
    private Filer filer;

    private Types typeUtils;
    private Elements elementUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();

        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(MongoStringCodec.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(MongoStringCodec.class);
        for (Element element : elements) {
            if (element.getKind() != ElementKind.RECORD) {
                error(String.format("Only classes can be annotated with @%s", MongoStringCodec.class.getSimpleName()), element);
                return true; // Exit processing
            }
            TypeMirror classTypeMirror = element.asType();
            ClassName className = getName(classTypeMirror);
            try {
                CodecBuilder codecBuilder = new CodecBuilder(filer, element, className);
                codecBuilder.generate();
                codecBuilder.generateCodecProvider();
            } catch (IOException e) {
                error(e.getMessage());
            }
        }
        return true;
    }

    private void error(String message, Element element) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void error(String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message);
    }

    private void note(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }

    private void checkForNoArgumentConstructor(TypeElement type) {
        for (ExecutableElement constructor :
                ElementFilter.constructorsIn(type.getEnclosedElements())) {
            List<? extends VariableElement> constructorParameters =
                    constructor.getParameters();
            if (constructor.getParameters().isEmpty()) {
                return;
            }
        }
//        processingEnv.getMessager().printMessage(
//                Diagnostic.Kind.ERROR,
//                "missing no argument constructor",
//                typeElement);
    }

    private ClassName getName(TypeMirror typeMirror) {
        String rawString = typeMirror.toString();
        int dotPosition = rawString.lastIndexOf(".");
        String packageName = rawString.substring(0, dotPosition);
        String className = rawString.substring(dotPosition + 1);
        return ClassName.get(packageName, className);
    }
}
