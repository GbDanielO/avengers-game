package br.com.avengers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server.direct}")
    private String direct;

    @Value("${swagger.server.gateway}")
    private String gateway;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Vilões")
                        .description("Documentação da API")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server().url(direct).description("API direta"),
                        new Server().url(gateway).description("Via Gateway")
                ));
    }

}
