package com.concertn.localbands.tools;

import org.jsoup.Jsoup;
import org.springframework.ai.tool.annotation.Tool;

import java.io.IOException;

public class EventAiTools {

    @Tool(description = "Get the body of a website in text/string")
    public String fetchWebsite(String url) throws IOException {
        return Jsoup.connect(url).get().body().text();
    }
}
