package com.github.ilyasyoy.script;

import com.github.ilyasyoy.script.configuration.properties.ScriptConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@ConditionalOnProperty(
        prefix = SpringScriptAutoConfiguration.SPRING_SCRIPT_PROPERTIES_PREFIX,
        name = ScriptConfigurationProperties.Fields.enable,
        havingValue = "true"
)
@ConditionalOnClass(ScriptEngineManager.class)
public class SpringScriptAutoConfiguration {
    public static final String SPRING_SCRIPT_PROPERTIES_PREFIX = "spring.script";

    @PostConstruct
    public void init() {
        List<ScriptEngineFactory> engineFactories = scriptEngineManager().getEngineFactories();

        List<String> engines = engineFactories.stream()
                .map(ScriptEngineFactory::getEngineName)
                .collect(Collectors.toList());

        log.info("Scripting engines available: {}", engines);
    }

    @Bean
    @Validated
    @ConfigurationProperties(prefix = SpringScriptAutoConfiguration.SPRING_SCRIPT_PROPERTIES_PREFIX)
    public ScriptConfigurationProperties scriptConfigurationProperties() {
        return new ScriptConfigurationProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public ScriptEngineManager scriptEngineManager() {
        return new ScriptEngineManager(SpringScriptAutoConfiguration.class.getClassLoader());
    }
}
