package com.concertn.localbands.services;

import org.jsoup.nodes.Document;

public interface JsoupService {

    public Document parseLink(String url);
}
