package com.concertn.localbands.tools;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class EventAiTools {

    @Tool(description = "Get the body of a website in text/string")
    public String fetchWebsite(String url) throws IOException {
        try {
            String text = Jsoup.connect(url)
                    .header("Accept-Encoding", "identity")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10_000)
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .get()
                    .body()
                    .text();
            log.debug("Content preview: {}", text);
            return text;
        } catch (IOException e) {
            return "Could not retrieve content from " + url + ": " + e.getMessage();
        }
    }
}
