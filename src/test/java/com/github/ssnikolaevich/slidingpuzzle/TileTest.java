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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TileTest {
    @Test
    public void testConstructor() {
        Tile tile = new Tile();
        assertEquals(0, tile.getPosition().getColumn());
        assertEquals(0, tile.getPosition().getRow());
        assertEquals(0, tile.getOrigin().getColumn());
        assertEquals(0, tile.getOrigin().getRow());
        assertEquals(1, tile.getColumns());
        assertEquals(1, tile.getRows());
        assertFalse(tile.isFixed());
    }

    @Test
    public void testGetRectangle() {
        Tile tile = new Tile();
        tile.setPosition(new Position(2, 3));
        tile.setColumns(4);
        tile.setRows(5);
        Rectangle r = tile.getRectangle();
        assertEquals(2, r.getLeft());
        assertEquals(3, r.getTop());
        assertEquals(5, r.getRight());
        assertEquals(7, r.getBottom());
    }

    @Test
    public void testFixed() {
        Tile tile = new Tile();
        assertFalse(tile.isFixed());
        tile.setFixed(true);
        assertTrue(tile.isFixed());
        tile.setFixed(false);
        assertFalse(tile.isFixed());
    }

    @Test
    public void testOrigin() {
        Tile tile = new Tile();
        Position origin = new Position(1, 2);
        tile.setOrigin(origin);
        assertEquals(origin, tile.getOrigin());
    }

    @Test
    public void testOnOrigin() {
        Tile tile = new Tile();
        tile.getOrigin().setColumn(1);
        tile.getOrigin().setRow(2);

        tile.getPosition().setColumn(3);
        tile.getPosition().setRow(4);
        assertFalse(tile.onOrigin());

        tile.getPosition().setColumn(3);
        tile.getPosition().setRow(2);
        assertFalse(tile.onOrigin());

        tile.getPosition().setColumn(1);
        tile.getPosition().setRow(2);
        assertTrue(tile.onOrigin());
    }

    @Test
    public void testCreateFromXML() throws ParserConfigurationException, IOException, SAXException {
        final String data = "<tile columns=\"2\" rows=\"3\" fixed=\"1\">"
                + "<position column=\"4\" row=\"5\"/>"
                + "<origin column=\"6\" row=\"7\"/>"
                + "</tile>";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Document document = documentBuilder.parse(inputStream);
        Element element = document.getDocumentElement();
        element.normalize();

        Tile tile = new Tile(document.getDocumentElement());
        assertTrue(tile.isFixed());
        assertEquals(2, tile.getColumns());
        assertEquals(3, tile.getRows());

        Position position = tile.getPosition();
        assertEquals(4, position.getColumn());
        assertEquals(5, position.getRow());

        Position origin = tile.getOrigin();
        assertEquals(6, origin.getColumn());
        assertEquals(7, origin.getRow());
    }
}
