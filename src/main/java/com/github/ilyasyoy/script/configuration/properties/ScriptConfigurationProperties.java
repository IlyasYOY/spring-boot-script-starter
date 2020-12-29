package com.github.ilyasyoy.script.configuration.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ScriptConfigurationProperties {
    @NotNull
    private Boolean enable = true;

    @NotEmpty
    private List<String> scriptsLocations = List.of("classpath:./scripts/");
}
