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
    public void testGetTileAt() {
        Collection<Tile> tiles = new ArrayList<>();
        Tile tile1 = new Tile();
        tile1.getPosition().setColumn(0);
        tile1.getPosition().setRow(1);
        tiles.add(tile1);

        Tile tile2 = new Tile();
        tile2.getPosition().setColumn(2);
        tile2.getPosition().setRow(3);
        tile2.setColumns(3);
        tile2.setRows(2);
        tiles.add(tile2);

        Game game = new Game(10, 10, "", tiles);
        assertNull(game.getTileAt(0, 0));
        assertNull(game.getTileAt(1, 2));
        assertNull(game.getTileAt(3, 5));
        assertNull(game.getTileAt(5, 4));
        assertEquals(tile1, game.getTileAt(0, 1));
        assertEquals(tile2, game.getTileAt(2, 3));
        assertEquals(tile2, game.getTileAt(4, 4));
    }

    @Test
    public void testGameIsOver() {
        Collection<Tile> tiles = new ArrayList<>();
        Tile tile = new Tile();
        tiles.add(tile);

        tile.getPosition().setColumn(1);
        tile.getPosition().setRow(2);
        tile.getOrigin().setColumn(1);
        tile.getOrigin().setRow(1);
        Game game = new Game(3, 3, "", tiles);
        assertFalse(game.isOver());

        tile.getPosition().setColumn(1);
        tile.getPosition().setRow(2);
        tile.getOrigin().setColumn(1);
        tile.getOrigin().setRow(2);
        game = new Game(3, 3, "", tiles);
        assertTrue(game.isOver());
    }

    @Test
    public void testGameEvents() {
        Collection<Tile> tiles = new ArrayList<>();
        Tile tile = new Tile();
        tile.getOrigin().setColumn(1);
        tile.getOrigin().setRow(1);
        tile.getPosition().setColumn(2);
        tile.getPosition().setRow(1);
        tiles.add(tile);

        final ArrayList<GameEvent> events = new ArrayList<>();
        GameListener listener = new GameListener() {
            @Override
            public void handle(GameEvent event) {
                events.add(event);
            }
        };

        Game game = new Game(3, 3, "", tiles);
        game.addListener(listener);
        game.makeMove(0, 0, Direction.DOWN);
        game.makeMove(2, 1, Direction.DOWN);
        game.removeListener(listener);
        game.makeMove(2, 2, Direction.LEFT);
        game.addListener(listener);
        game.addListener(listener);
        game.makeMove(1, 2, Direction.UP);
        game.removeListener(listener);
        game.makeMove(1, 1, Direction.RIGHT);

        assertEquals(4, events.size());

        assertEquals(GameEvent.EventType.MOVE, events.get(0).getType());
        assertEquals(Direction.DOWN, events.get(0).getDirection());
        assertTrue(events.get(0).getPushedTiles().isEmpty());

        assertEquals(GameEvent.EventType.MOVE, events.get(1).getType());
        assertEquals(Direction.DOWN, events.get(1).getDirection());
        assertTrue(events.get(1).getPushedTiles().contains(tile));

        assertEquals(GameEvent.EventType.MOVE, events.get(2).getType());
        assertEquals(Direction.UP, events.get(2).getDirection());
        assertTrue(events.get(2).getPushedTiles().contains(tile));

        assertEquals(GameEvent.EventType.GAME_OVER, events.get(3).getType());
    }

    @Test
    public void testMakeMove() {
        ArrayList<Tile> tiles = new ArrayList<>();

        Tile tile = new Tile();
        tile.getPosition().setColumn(1);
        tile.setFixed(true);
        tiles.add(tile);

        tile = new Tile();
        tile.getPosition().setRow(1);
        tiles.add(tile);

        tile = new Tile();
        tile.getPosition().setColumn(1);
        tile.getPosition().setRow(1);
        tiles.add(tile);

        tile = new Tile();
        tile.getPosition().setColumn(2);
        tile.getPosition().setRow(1);
        tiles.add(tile);

        tile = new Tile();
        tile.getPosition().setColumn(1);
        tile.getPosition().setRow(2);
        tiles.add(tile);

        tile = new Tile();
        tile.getPosition().setColumn(2);
        tile.getPosition().setRow(2);
        tile.setRows(2);
        tile.setColumns(1);
        tiles.add(tile);

        Game game = new Game(3, 4, "", tiles);

        game.makeMove(1, 2, Direction.UP);
        assertEquals(1, tiles.get(0).getPosition().getColumn());
        assertEquals(0, tiles.get(0).getPosition().getRow());
        assertEquals(0, tiles.get(1).getPosition().getColumn());
        assertEquals(1, tiles.get(1).getPosition().getRow());
        assertEquals(1, tiles.get(2).getPosition().getColumn());
        assertEquals(1, tiles.get(2).getPosition().getRow());
        assertEquals(2, tiles.get(3).getPosition().getColumn());
        assertEquals(1, tiles.get(3).getPosition().getRow());
        assertEquals(1, tiles.get(4).getPosition().getColumn());
        assertEquals(2, tiles.get(4).getPosition().getRow());
        assertEquals(2, tiles.get(5).getPosition().getColumn());
        assertEquals(2, tiles.get(5).getPosition().getRow());

        game.makeMove(2, 3, Direction.DOWN);
        assertEquals(1, tiles.get(0).getPosition().getColumn());
        assertEquals(0, tiles.get(0).getPosition().getRow());
        assertEquals(0, tiles.get(1).getPosition().getColumn());
        assertEquals(1, tiles.get(1).getPosition().getRow());
        assertEquals(1, tiles.get(2).getPosition().getColumn());
        assertEquals(1, tiles.get(2).getPosition().getRow());
        assertEquals(2, tiles.get(3).getPosition().getColumn());
        assertEquals(1, tiles.get(3).getPosition().getRow());
        assertEquals(1, tiles.get(4).getPosition().getColumn());
        assertEquals(2, tiles.get(4).getPosition().getRow());
        assertEquals(2, tiles.get(5).getPosition().getColumn());
        assertEquals(2, tiles.get(5).getPosition().getRow());

        game.makeMove(2, 1, Direction.LEFT);
        assertEquals(1, tiles.get(0).getPosition().getColumn());
        assertEquals(0, tiles.get(0).getPosition().getRow());
        assertEquals(2, tiles.get(1).getPosition().getColumn());
        assertEquals(1, tiles.get(1).getPosition().getRow());
        assertEquals(0, tiles.get(2).getPosition().getColumn());
        assertEquals(1, tiles.get(2).getPosition().getRow());
        assertEquals(1, tiles.get(3).getPosition().getColumn());
        assertEquals(1, tiles.get(3).getPosition().getRow());
        assertEquals(1, tiles.get(4).getPosition().getColumn());
        assertEquals(2, tiles.get(4).getPosition().getRow());
        assertEquals(2, tiles.get(5).getPosition().getColumn());
        assertEquals(2, tiles.get(5).getPosition().getRow());

        game.makeMove(2, 3, Direction.UP);
        assertEquals(1, tiles.get(0).getPosition().getColumn());
        assertEquals(0, tiles.get(0).getPosition().getRow());
        assertEquals(2, tiles.get(1).getPosition().getColumn());
        assertEquals(0, tiles.get(1).getPosition().getRow());
        assertEquals(0, tiles.get(2).getPosition().getColumn());
        assertEquals(1, tiles.get(2).getPosition().getRow());
        assertEquals(1, tiles.get(3).getPosition().getColumn());
        assertEquals(1, tiles.get(3).getPosition().getRow());
        assertEquals(1, tiles.get(4).getPosition().getColumn());
        assertEquals(2, tiles.get(4).getPosition().getRow());
        assertEquals(2, tiles.get(5).getPosition().getColumn());
        assertEquals(1, tiles.get(5).getPosition().getRow());

        game.makeMove(2, 1, Direction.RIGHT);
        assertEquals(1, tiles.get(0).getPosition().getColumn());
        assertEquals(0, tiles.get(0).getPosition().getRow());
        assertEquals(2, tiles.get(1).getPosition().getColumn());
        assertEquals(0, tiles.get(1).getPosition().getRow());
        assertEquals(1, tiles.get(2).getPosition().getColumn());
        assertEquals(1, tiles.get(2).getPosition().getRow());
        assertEquals(2, tiles.get(3).getPosition().getColumn());
        assertEquals(1, tiles.get(3).getPosition().getRow());
        assertEquals(1, tiles.get(4).getPosition().getColumn());
        assertEquals(2, tiles.get(4).getPosition().getRow());
        assertEquals(0, tiles.get(5).getPosition().getColumn());
        assertEquals(1, tiles.get(5).getPosition().getRow());

        game.makeMove(1, 2, Direction.DOWN);
        assertEquals(1, tiles.get(0).getPosition().getColumn());
        assertEquals(0, tiles.get(0).getPosition().getRow());
        assertEquals(2, tiles.get(1).getPosition().getColumn());
        assertEquals(0, tiles.get(1).getPosition().getRow());
        assertEquals(1, tiles.get(2).getPosition().getColumn());
        assertEquals(1, tiles.get(2).getPosition().getRow());
        assertEquals(2, tiles.get(3).getPosition().getColumn());
        assertEquals(1, tiles.get(3).getPosition().getRow());
        assertEquals(1, tiles.get(4).getPosition().getColumn());
        assertEquals(3, tiles.get(4).getPosition().getRow());
        assertEquals(0, tiles.get(5).getPosition().getColumn());
        assertEquals(1, tiles.get(5).getPosition().getRow());

        game.makeMove(1, 3, Direction.LEFT);
        assertEquals(1, tiles.get(0).getPosition().getColumn());
        assertEquals(0, tiles.get(0).getPosition().getRow());
        assertEquals(2, tiles.get(1).getPosition().getColumn());
        assertEquals(0, tiles.get(1).getPosition().getRow());
        assertEquals(1, tiles.get(2).getPosition().getColumn());
        assertEquals(1, tiles.get(2).getPosition().getRow());
        assertEquals(2, tiles.get(3).getPosition().getColumn());
        assertEquals(1, tiles.get(3).getPosition().getRow());
        assertEquals(0, tiles.get(4).getPosition().getColumn());
        assertEquals(3, tiles.get(4).getPosition().getRow());
        assertEquals(0, tiles.get(5).getPosition().getColumn());
        assertEquals(1, tiles.get(5).getPosition().getRow());

        game.makeMove(2, 0, Direction.UP);
        assertEquals(1, tiles.get(0).getPosition().getColumn());
        assertEquals(0, tiles.get(0).getPosition().getRow());
        assertEquals(2, tiles.get(1).getPosition().getColumn());
        assertEquals(3, tiles.get(1).getPosition().getRow());
        assertEquals(1, tiles.get(2).getPosition().getColumn());
        assertEquals(1, tiles.get(2).getPosition().getRow());
        assertEquals(2, tiles.get(3).getPosition().getColumn());
        assertEquals(1, tiles.get(3).getPosition().getRow());
        assertEquals(0, tiles.get(4).getPosition().getColumn());
        assertEquals(3, tiles.get(4).getPosition().getRow());
        assertEquals(0, tiles.get(5).getPosition().getColumn());
        assertEquals(1, tiles.get(5).getPosition().getRow());
    }

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
