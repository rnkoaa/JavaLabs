package com.marketplace.eventstoredb.framework;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target(value = {ElementType.FIELD, ElementType.METHOD})
public @interface AggregateIdentifier {
}
