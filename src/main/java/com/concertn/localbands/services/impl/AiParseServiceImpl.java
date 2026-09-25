package com.concertn.localbands.services.impl;

import com.concertn.localbands.domain.dtos.AIEventResponseDto;
import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import com.concertn.localbands.services.AiParseService;
import com.concertn.localbands.tools.EventAiTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AiParseServiceImpl implements AiParseService {

    private final ChatClient venueParseClient;


    @Override
    public List<AIEventResponseDto> ParsePlaces(NearbySearchResponse.Place place) {
        PromptTemplate promptTemplate = new PromptTemplate("Can you give me events at {name}, the website is {website}");

        Prompt prompt = new Prompt(
                promptTemplate.create(Map.of(
                        "name", place.displayName().text(),
                        "website", place.websiteUri())).getInstructions(),
                OpenAiChatOptions.builder()
                        .maxCompletionTokens(5000)
                        .build()
        );

        return venueParseClient.prompt(prompt)
                .tools(new EventAiTools())
                .advisors(new SimpleLoggerAdvisor()) // used to debug, see what ai is up to
                .call()
                .entity(new ParameterizedTypeReference<List<AIEventResponseDto>>() {});

    }
}
