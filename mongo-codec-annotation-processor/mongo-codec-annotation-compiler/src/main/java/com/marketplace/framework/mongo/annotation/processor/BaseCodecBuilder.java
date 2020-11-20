package com.marketplace.framework.mongo.annotation.processor;

import com.squareup.javapoet.ClassName;

import javax.lang.model.type.TypeMirror;

public abstract class BaseCodecBuilder {

    protected ClassName getClassName(TypeMirror typeMirror) {
        String rawString = typeMirror.toString();
        int dotPosition = rawString.lastIndexOf(".");
        String packageName = rawString.substring(0, dotPosition);
        String className = rawString.substring(dotPosition + 1);
        return ClassName.get(packageName, className);
    }
}
