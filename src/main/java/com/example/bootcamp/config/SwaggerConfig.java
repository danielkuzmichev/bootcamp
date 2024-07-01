package com.example.bootcamp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
            .servers(
                List.of(
                    new Server().url("http://localhost:8080/api")
                ))
            .info(new Info()
                .title("Bootcamp API")
                .version("1.0")
                .description("API for registering a candidate for a training camp")
                .contact(new Contact()
                    .name("Кузьмичев Даниил")
                    .email("kuzmichev.dan-00@yandex.ru")
                )
            );
    }
}
