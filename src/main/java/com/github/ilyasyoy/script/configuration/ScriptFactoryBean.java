package com.github.ilyasyoy.script.configuration;

import com.github.ilyasyoy.script.configuration.annotation.Function;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Proxy;
import java.util.Map;

public class ScriptFactoryBean implements FactoryBean<Object>, InitializingBean, ApplicationContextAware {

    private String name;

    private Class<?> type;

    @Setter
    private ApplicationContext applicationContext;

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(applicationContext.getClassLoader(), new Class[]{type}, (proxy, method, args) -> {
            Function annotation = AnnotationUtils.findAnnotation(method, Function.class);

            Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);

            String functionName = (String) annotationAttributes.get("name");

            // TODO: Compile Script
            // TODO: Run Script

            return null;
        });
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasText(name, "Name must be specified");
    }

}
