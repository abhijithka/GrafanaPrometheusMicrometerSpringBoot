package com.example.loggingconsumer;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Abhijith
 */
@Configuration
public class OpenApiConfig {

    private static final String PATH_ACTUATOR_APIS = "/actuator/**";

    private static final String GROUP_NAME = "Day Of Glory";

    private static final String TITLE = "Day of Glory - APIs";

    @Bean
    public GroupedOpenApi appAPI() {
        return GroupedOpenApi.builder().group(GROUP_NAME).build();
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info().title(TITLE).version(appVersion));
    }

}
