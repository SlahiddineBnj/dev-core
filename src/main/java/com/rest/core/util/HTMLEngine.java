package com.rest.core.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Component
public class HTMLEngine {

    @Autowired
    private ResourceLoader resourceLoader ;

    public  String injectValues(Document document , Map<String,String> data){
        data.forEach((key, value)->{
            document.select(key).first().text(value) ;
        });
        return document.html();
    }


    public Document getDocument(String fileName) throws URISyntaxException, IOException {
////        File input = new File(Objects.requireNonNull(ClassLoader
////                .getSystemResource(String.format("static/html_documents/%s", fileName))).toExternalForm());
//        URI uri = URI.create("https://store4.gofile.io/download/3b3a271d-d3d8-41f1-8ee6-713bf6e0ffbc/") ;
//        URL url = new URL(uri.toURL(), "signup_letter.html") ;
//        System.out.println(url.toString());
////        URL url = new URL("https://store4.gofile.io/download/3b3a271d-d3d8-41f1-8ee6-713bf6e0ffbc/signup_letter.html") ;
////        String tmpPath = System.getProperty("java.io.tmpDir") ;
////        String path = tmpPath + "tmp_letter.html" ;
////        File input = new File(Objects.requireNonNull(path).toURI());
////        input.deleteOnExit();
//        File file = new File(url.getFile()) ;
//        if (file.exists()) {
//            System.out.println("file "+file);
//        }
//
////        file.deleteOnExit();
//
////        FileUtils.copyURLToFile(url,file);

        Resource resource = resourceLoader.getResource("static/html/signup_letter.html") ;
        InputStream inputStream = resource.getInputStream() ;

        File file = new File("/tmp/tmp.html") ;
        Files.copy(inputStream,file.toPath(), StandardCopyOption.REPLACE_EXISTING) ;

        return Jsoup.parse(file, "UTF-8") ;
    }




}
