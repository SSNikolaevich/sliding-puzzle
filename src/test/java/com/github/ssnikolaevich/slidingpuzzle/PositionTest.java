/*
 * Copyright 2015 Sergey Skuratovich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.ssnikolaevich.slidingpuzzle;

import static org.junit.Assert.*;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PositionTest {
    @Test
    public void testDefaultConstructor() {
        Position position = new Position();
        assertEquals(0, position.getColumn());
        assertEquals(0, position.getRow());
    }

    @Test
    public void testConstructor() {
        Position position = new Position(2, 3);
        assertEquals(2, position.getColumn());
        assertEquals(3, position.getRow());
    }

    @Test
    public void testSetColumn() {
        Position position = new Position();

        position.setColumn(2);
        assertEquals(2, position.getColumn());
        position.setColumn(5);
        assertEquals(5, position.getColumn());
    }

    @Test
    public void testSetRow() {
        Position position = new Position();

        position.setRow(3);
        assertEquals(3, position.getRow());
        position.setRow(6);
        assertEquals(6, position.getRow());
    }

    @Test
    public void testCreateFromXML() throws ParserConfigurationException, IOException, SAXException {
        final String data = "<position column=\"4\" row=\"5\"/>";

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Document document = documentBuilder.parse(inputStream);
        Element element = document.getDocumentElement();
        element.normalize();

        Position position = new Position(document.getDocumentElement());
        assertEquals(4, position.getColumn());
        assertEquals(5, position.getRow());
    }
}
