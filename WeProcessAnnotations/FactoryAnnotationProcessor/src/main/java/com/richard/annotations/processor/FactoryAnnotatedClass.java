package com.richard.annotations.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import com.richard.annotations.Factory;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by U0165547 on 4/22/2015.
 */

/**
 * Holds the information about a class annotated with @Factory
 *
 * @author Hannes Dorfmann
 */
public class FactoryAnnotatedClass {

    private TypeElement annotatedClassElement;
    private String qualifiedGroupClassName;
    private String simpleFactoryGroupName;
    private String id;

    public FactoryAnnotatedClass(TypeElement classElement) throws IllegalArgumentException {
        this.annotatedClassElement = classElement;
        Factory annotation = classElement.getAnnotation(Factory.class);
        id = annotation.id();

        if (StringUtils.isEmpty(id)) {
            throw new IllegalArgumentException(
                    String.format("id() in @%s for class %s is null or empty! that's not allowed",
                            Factory.class.getSimpleName(), classElement.getQualifiedName().toString()));
        }

        // Get the full QualifiedTypeName
        try {
            Class<?> clazz = annotation.type();
            qualifiedGroupClassName = clazz.getCanonicalName();
            simpleFactoryGroupName = clazz.getSimpleName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedGroupClassName = classTypeElement.getQualifiedName().toString();
            simpleFactoryGroupName = classTypeElement.getSimpleName().toString();
        }
    }

    /**
     * Get the id as specified in {@link Factory#id()}.
     * return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Get the full qualified name of the type specified in  {@link Factory#type()}.
     *
     * @return qualified name
     */
    public String getQualifiedFactoryGroupName() {
        return qualifiedGroupClassName;
    }

    /**
     * Get the simple name of the type specified in  {@link Factory#type()}.
     *
     * @return qualified name
     */
    public String getSimpleFactoryGroupName() {
        return simpleFactoryGroupName;
    }

    /**
     * The original element that was annotated with @Factory
     */
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }
}
