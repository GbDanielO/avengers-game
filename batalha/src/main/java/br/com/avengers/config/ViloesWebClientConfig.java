package br.com.avengers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ViloesWebClientConfig {

    @Bean
    public RestClient viloesRestClient(
            RestClient.Builder builder,
            @Value("${clients.viloes.base-url}") String baseUrl) {

        return builder
                .baseUrl(baseUrl)
                .build();
    }

}
