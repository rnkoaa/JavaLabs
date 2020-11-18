package com.marketplace.framework.mongo.annotation.processor;

import com.google.auto.service.AutoService;
import com.marketplace.framework.mongo.annotations.MongoRecordValue;
import com.marketplace.framework.mongo.annotations.MongoStringCodec;
import com.squareup.javapoet.ClassName;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
public class MongoRecordValueProcessor extends AbstractProcessor {
    private Filer filer;

    //    private Types typeUtils;
//    private Elements elementUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();

//        typeUtils = processingEnv.getTypeUtils();
//        elementUtils = processingEnv.getElementUtils();
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(MongoRecordValue.class);
        for (Element element : elements) {
            if (element.getKind() != ElementKind.RECORD) {
                error(String.format("Only classes can be annotated with @%s", MongoStringCodec.class.getSimpleName()), element);
                return true; // Exit processing
            }



            try {
                MongoRecordValueCodecBuilder codecBuilder = new MongoRecordValueCodecBuilder(filer, element, messager);
                codecBuilder.generateCodec();
                codecBuilder.generateCodecProvider();
            } catch (IOException e) {
                error(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(MongoRecordValue.class.getCanonicalName());
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
    }

    private ClassName getName(TypeMirror typeMirror) {
        String rawString = typeMirror.toString();
        int dotPosition = rawString.lastIndexOf(".");
        String packageName = rawString.substring(0, dotPosition);
        String className = rawString.substring(dotPosition + 1);
        return ClassName.get(packageName, className);
    }
}
