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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class GameTest {
    @Test
    public void testCreateFromXML() throws ParserConfigurationException, IOException, SAXException {
        final String data = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<level columns=\"3\" rows=\"4\" gliph=\"gliphName\">"
                + "<tile columns=\"1\" rows=\"1\" fixed=\"0\">"
                + "<origin column=\"0\" row=\"0\"/>"
                + "<position column=\"0\" row=\"1\"/>"
                + "</tile>"
                + "<tile columns=\"1\" rows=\"2\" fixed=\"1\">"
                + "<origin column=\"1\" row=\"1\"/>"
                + "<position column=\"2\" row=\"0\"/>"
                + "</tile>"
                + "</level>";

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        Document document = documentBuilder.parse(inputStream);
        Element element = document.getDocumentElement();
        element.normalize();

        Game game = new Game(element);

        assertEquals(3, game.getColumns());
        assertEquals(4, game.getRows());
        assertEquals("gliphName", game.getGliph());

        Collection<Tile> tiles = game.getTiles();
        assertEquals(2, tiles.size());
        Iterator<Tile> i = tiles.iterator();
        Tile tile = i.next();
        assertEquals(1, tile.getColumns());
        assertEquals(1, tile.getRows());
        assertFalse(tile.isFixed());
        tile = i.next();
        assertEquals(1, tile.getColumns());
        assertEquals(2, tile.getRows());
        assertTrue(tile.isFixed());
    }
}
