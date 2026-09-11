package com.concertn.localbands.services.impl;

import com.concertn.localbands.services.JsoupService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class JsoupServiceImpl implements JsoupService {
    @Override
    public Document parseLink(String url) {

        try{
            Document doc = Jsoup.connect(url).get();

            return doc;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
