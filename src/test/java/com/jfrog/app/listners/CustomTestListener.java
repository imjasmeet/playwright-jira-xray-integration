package com.jfrog.app.listners;

import app.getxray.xray.testng.annotations.XrayTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.lang.reflect.Method;

public class CustomTestListener implements ITestListener {

    private Document doc;
    private Element rootElement;

    @Override
    public void onStart(ITestContext context) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            doc = docBuilder.newDocument();
            rootElement = doc.createElement("testsuite");
            doc.appendChild(rootElement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();
        if (method.isAnnotationPresent(XrayTest.class)) {
            XrayTest xrayTest = method.getAnnotation(XrayTest.class);

            // Add xrayTest properties to the test result
            result.setAttribute("XrayTestKey", xrayTest.key());
            result.setAttribute("XrayTestLabels", xrayTest.labels());
            result.setAttribute("XrayTestSummary", xrayTest.summary());
            result.setAttribute("XrayTestDescription", xrayTest.description());

            // Create a new XML element for the test
            Element testElement = doc.createElement("testcase");
            testElement.setAttribute("name", method.getName());
            testElement.setAttribute("XrayTestKey", xrayTest.key());
            testElement.setAttribute("XrayTestLabels", xrayTest.labels());
            testElement.setAttribute("XrayTestSummary", xrayTest.summary());
            testElement.setAttribute("XrayTestDescription", xrayTest.description());

            // Add the test element to the root element
            rootElement.appendChild(testElement);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("test-output/xrayTestReport.xml"));

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}