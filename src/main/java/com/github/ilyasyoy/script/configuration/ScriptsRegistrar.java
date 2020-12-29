package com.github.ilyasyoy.script.configuration;

import com.github.ilyasyoy.script.configuration.annotation.EnableScripts;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class ScriptsRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata attributes, BeanDefinitionRegistry registry) {
        String annotationName = EnableScripts.class.getName();
        Map<String, Object> annotationAttributes = attributes.getAnnotationAttributes(annotationName, true);

        String[] basePackages = (String[]) annotationAttributes.get("basePackages");
        Class<?>[] scripts = (Class<?>[]) annotationAttributes.get("scripts");

        registerPackages(basePackages, registry);
        registerScripts(basePackages, registry);
    }

    private void registerPackages(String[] basePackages, BeanDefinitionRegistry registry) {

    }

    private void registerScripts(String[] basePackages, BeanDefinitionRegistry registry) {

    }
}
