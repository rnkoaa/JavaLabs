package com.marketplace.framework.mongo.annotations;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface MongoStringValueCodec {

    String[] value() default "";
}