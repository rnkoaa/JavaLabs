package org.richard.vavr.intro;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.immutables.value.Value;

@Target(ElementType.TYPE)
@Value.Style(
    typeBuilder = "BuilderFor_*",
    defaultAsDefault = true, // java 8 default methods will be automatically turned into @Value.Default
    visibility = Value.Style.ImplementationVisibility.PRIVATE,
    builderVisibility = Value.Style.BuilderVisibility.PACKAGE) // We will extend builder to make it public
public @interface Builded {

}
