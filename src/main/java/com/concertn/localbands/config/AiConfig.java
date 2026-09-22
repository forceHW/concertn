package com.concertn.localbands.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AiConfig {

    @Value("classpath:/prompts/venue-sys.st")
    private Resource systemResource;

    @Bean
    public ChatClient venueParseClient(ChatClient.Builder builder){
        return builder
                .defaultSystem(systemResource)
                .build();
    }
}
