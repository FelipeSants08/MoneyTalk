package com.santana.moneytalk.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customizarOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Money Talk")
                        .version("1.0")
                        .contact(new Contact().name("Felipe")
                                .email("felipe_santana.01@hotmail.com"))
                        .description("Controle financeiro com ajuda de IA"));
    }

}
