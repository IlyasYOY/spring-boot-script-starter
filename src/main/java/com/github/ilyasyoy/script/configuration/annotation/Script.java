package com.github.ilyasyoy.script.configuration.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Script {
    @AliasFor("name")
    String value() default "";
    
    @AliasFor("value")
    String name() default "";
}
