package com.matheusorestes.dndCharacterSheet.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient dndWebClient() {
        return WebClient.builder()
                .baseUrl("https://www.dnd5eapi.co")
                .build();
    }
}