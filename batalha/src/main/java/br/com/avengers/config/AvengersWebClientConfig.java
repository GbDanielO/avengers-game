package br.com.avengers.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AvengersWebClientConfig {

    @Bean
    public RestClient avengersRestClient(
            RestClient.Builder builder,
            @Value("${clients.avengers.base-url}") String baseUrl) {

        return builder
                .baseUrl(baseUrl)
                .build();
    }

}
