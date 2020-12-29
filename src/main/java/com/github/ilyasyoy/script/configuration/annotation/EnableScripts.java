package com.github.ilyasyoy.script.configuration.annotation;

import com.github.ilyasyoy.script.configuration.ScriptsRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ScriptsRegistrar.class)
public @interface EnableScripts {
    Class<?>[] scripts() default {};

    String[] basePackages() default {};
}
