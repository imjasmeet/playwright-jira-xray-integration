package com.jfrog.app.utils;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class xmlValidator {

public void validateXML(String filePath) {
    try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(filePath);
        System.out.println(filePath + " is valid.");
    } catch (Exception e) {
        System.out.println(filePath + " is not valid due to " + e.getMessage());
    }
}
}
