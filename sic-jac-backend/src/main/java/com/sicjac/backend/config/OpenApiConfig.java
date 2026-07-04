package com.sicjac.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sicJacOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SIC JAC Backend API")
                        .description("API REST para gestion de usuarios, productos e inventario.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("SIC JAC")
                                .email("soporte@sicjac.local")));
    }
}
