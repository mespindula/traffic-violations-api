package com.desafio.trafficviolations.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Cadastro de Infrações de Trânsito")
                        .description("Documentação da API de cadastro e consulta de infrações")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Maicon Espindula")
                                .email("mespindula@gmail.com"))
                        .termsOfService("Terms of Service")
                        .license(new License()
                                .name("Apache 2.0")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Repositório no GitHub")
                                .url("https://github.com/seu-usuario/seu-projeto"));
    }

}
