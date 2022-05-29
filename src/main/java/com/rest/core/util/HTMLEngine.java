package com.rest.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

@Component
public class HTMLEngine {

    public  String injectValues(Document document , Map<String,String> data){
        data.forEach((key, value)->{
            document.select(key).first().text(value) ;
        });
        return document.html();
    }



    public Document getDocument(String fileName) throws URISyntaxException, IOException {
        URL path = getClass().getClassLoader().getResource(String.format("static/html_documents/%s",fileName));
        File input = new File(Objects.requireNonNull(path).toURI());
        return Jsoup.parse(input, "UTF-8") ;
    }




}
