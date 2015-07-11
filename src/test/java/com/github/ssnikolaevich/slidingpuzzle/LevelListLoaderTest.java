package com.github.ssnikolaevich.slidingpuzzle;

import static org.junit.Assert.*;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LevelListLoaderTest {
    @Test
    public void testLoad() throws ParserConfigurationException, IOException, SAXException {
        final String data = "<levels><level name=\"l1\"/><level name=\"l2\"/><level name=\"l3\"/></levels>";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Document document = documentBuilder.parse(inputStream);
        Element element = document.getDocumentElement();
        element.normalize();

        ArrayList<String> levels = LevelListLoader.load(element);
        final String[] referenceLevels = {"l1", "l2", "l3"};
        assertArrayEquals(referenceLevels, levels.toArray());
    }
}
