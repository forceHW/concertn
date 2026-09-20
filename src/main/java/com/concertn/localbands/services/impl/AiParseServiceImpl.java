package com.concertn.localbands.services.impl;

import com.concertn.localbands.domain.dtos.NearbySearchResponse;
import com.concertn.localbands.services.AiParseService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AiParseServiceImpl implements AiParseService {

    private final ChatClient venueParseClient;


    @Override
    public Boolean ParsePlaces(NearbySearchResponse nearbySearchResponse) {
        PromptTemplate promptTemplate = new PromptTemplate("Can you give me events at {name}, the website is {website}");


//        resp = this.chatClient.prompt()
    }
}
