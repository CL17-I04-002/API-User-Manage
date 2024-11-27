package com.project.user.manage.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "API Key Service",
                version = "1.0",
                description = "API Key Authentication"
        ),
        security = {
                @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "apiKeyScheme")
        }
)
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "apiKeyScheme",
        description = "API Key authentication",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.HEADER,
        paramName = "X-API-KEY" // Nombre del key
)
/**
 * This class is helpful to define API Key method in the UI
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiKeyOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList("apiKeyScheme"))
                .components(new Components()
                        .addSecuritySchemes(
                                "apiKeyScheme", new SecurityScheme()
                                        .name("X-API-KEY") // Nombre del key
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                        )
                )
                .info(new Info()
                        .title("API Key Management Service")
                        .description("API Key authentication example")
                        .version("1.0"));
    }
}
