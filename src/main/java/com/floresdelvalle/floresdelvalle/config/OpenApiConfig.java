package com.floresdelvalle.floresdelvalle.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI floresDelValleOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Flores del Valle")
                        .description("Documentación de la API REST de la aplicación web Flores del Valle")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo de desarrollo")
                                .email("contacto@floresdelvalle.com")));
    }
}