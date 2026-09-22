package com.concertn.localbands.tools;

import org.jsoup.Jsoup;
import org.springframework.ai.tool.annotation.Tool;

import java.io.IOException;

public class EventAiTools {

    @Tool(description = "Get the body of a website in text/string")
    public String fetchWebsite(String url) throws IOException {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10_000)
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            return "Could not retrieve content from " + url + ": " + e.getMessage();
        }
    }
}
