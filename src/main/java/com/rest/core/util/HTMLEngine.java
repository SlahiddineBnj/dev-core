package com.rest.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class HTMLEngine {

    public static String injectValues(Document document , Map<String,String> data){
        data.forEach((key, value)->{
            document.select(key).first().text(value) ;
        });
        return document.html();
    }



    public static Document getDocument(String fileName) throws URISyntaxException, IOException {
        URL path = ClassLoader.getSystemResource(String.format("static/html_documents/%s",fileName));
        File input = new File(path.toURI());
        return Jsoup.parse(input, "UTF-8") ;
    }




}
