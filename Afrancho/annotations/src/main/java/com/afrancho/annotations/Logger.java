package com.afrancho.annotations;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Logger {
    String[] values() default "";
}
