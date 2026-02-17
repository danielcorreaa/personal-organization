package com.personal_organization.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI projectOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Management API")
                        .description("""
                                API para gerenciamento de projetos do usuário
                                e configurações globais de ProjectType.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Seu Nome / Time")
                                .email("dev@yourcompany.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}
