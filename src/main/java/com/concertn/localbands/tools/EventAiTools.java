package com.concertn.localbands.tools;

import com.concertn.localbands.domain.dtos.PageDataDto;
import com.concertn.localbands.exceptions.AiException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class EventAiTools {

    @Tool(description = "Get data on a page, provides the page content and the links of the other paths")
    public PageDataDto fetchWebsite(String url) throws IOException {
        try {
            Document doc = Jsoup.connect(url)
                    .header("Accept-Encoding", "identity")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10_000)
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .get();
//            log.debug("Content preview: {}", text);
            return new PageDataDto(fetchStringDump(doc),fetchPaths(doc));
        } catch (IOException e) {
//            throw err?
            return null;
        }
    }


//    @Tool(description = "Get the Document Object of a given website")
//    public Document fetchJsoupDocument(String url) throws IOException {
//        try {
//            return Jsoup.connect(url)
//                    .header("Accept-Encoding", "identity")
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
//                    .timeout(10_000)
//                    .followRedirects(true)
//                    .ignoreHttpErrors(true)
//                    .get();
//        } catch (IOException e) {
////            return "Could not retrieve Doc Object from " + url + ": " + e.getMessage();
//            return null;
//        }
//    }
//
//    @Tool(description = "Given a document argument, return a string dump of the body")
    private String fetchStringDump(Document doc){
        return doc.body().text();
    }
//
//    @Tool(description = "Given a document argument, return all the other paths")
    private List<String> fetchPaths(Document doc){
        Elements paths = doc.select("a[href]");

        return paths.stream().map(element ->
            element.absUrl("href"))
                .filter(href -> !href.isBlank())
                .distinct()
                .toList();
    }

    @Tool(description = "Throws an error")
    public void throwError(String error){
        throw new AiException(error);
    }
}
