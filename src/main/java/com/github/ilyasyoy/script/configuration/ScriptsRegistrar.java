package com.github.ilyasyoy.script.configuration;

import com.github.ilyasyoy.script.configuration.annotation.EnableScripts;
import com.github.ilyasyoy.script.configuration.annotation.Script;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ScriptsRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    public static final Class<Script> SCRIPT_TYPE_ANNOTATION = Script.class;

    @Setter
    private ResourceLoader resourceLoader;

    @Setter
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata attributes, BeanDefinitionRegistry registry) {
        String annotationName = EnableScripts.class.getName();
        Map<String, Object> annotationAttributes = attributes.getAnnotationAttributes(annotationName, true);

        String[] basePackages = (String[]) annotationAttributes.get("basePackages");
        Class<?>[] scripts = (Class<?>[]) annotationAttributes.get("scripts");

        registerPackages(registry, Arrays.asList(basePackages));
        registerScripts(registry, Arrays.asList(scripts));
    }

    private void registerPackages(BeanDefinitionRegistry registry, Collection<String> basePackages) {
        Reflections reflections = new Reflections(basePackages.toArray());

        Set<Class<?>> typesAnnotatedWithScript = reflections.getTypesAnnotatedWith(SCRIPT_TYPE_ANNOTATION);

        registerScripts(registry, typesAnnotatedWithScript);
    }

    private void registerScripts(BeanDefinitionRegistry registry, Collection<Class<?>> scriptClasses) {
        for (Class<?> scriptClass : scriptClasses) {
            registerScriptClas(registry, scriptClass);
        }
    }

    private void registerScriptClas(BeanDefinitionRegistry registry, Class<?> scriptClass) {
        Assert.isTrue(scriptClass.isInterface(), String.format("You cannot use class as instance of Script on %s", scriptClass.getName()));

        Script annotation = AnnotationUtils.findAnnotation(scriptClass, Script.class);

        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);

        String name = (String) annotationAttributes.get("name");

        BeanDefinitionBuilder factoryBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ScriptFactoryBean.class)
                .addPropertyValue("name", name)
                .addPropertyValue("type", scriptClass.getName())
                .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

        AbstractBeanDefinition factoryBeanDefinition = factoryBeanDefinitionBuilder.getBeanDefinition();
        factoryBeanDefinition.setAttribute(FactoryBean.OBJECT_TYPE_ATTRIBUTE, scriptClass);


        BeanDefinitionHolder holder = new BeanDefinitionHolder(factoryBeanDefinition, scriptClass.getName());
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }
}
