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
