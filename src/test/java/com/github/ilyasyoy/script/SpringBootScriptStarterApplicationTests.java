package com.github.ilyasyoy.script;

import com.github.ilyasyoy.script.configuration.properties.ScriptConfigurationProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;

import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SpringBootScriptStarterApplicationTests {

    @Autowired
    private ScriptEngineManager scriptEngineManager;

    @Autowired
    private ScriptConfigurationProperties scriptConfigurationProperties;

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    void createsScriptEngineManager() {
        List<String> scriptsLocations = scriptConfigurationProperties.getScriptsLocations();
        assertThat(scriptEngineManager).isNotNull();
    }

}

@SpringBootApplication
class Application {
}
