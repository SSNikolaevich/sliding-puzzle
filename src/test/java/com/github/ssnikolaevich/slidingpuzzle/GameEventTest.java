package com.github.ssnikolaevich.slidingpuzzle;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class GameEventTest {
    @Test
    public void testGameOverEvent() {
        GameEvent event = GameEvent.gameOverEvent(this);
        assertEquals(GameEvent.EventType.GAME_OVER, event.getType());
    }

    @Test
    public void testMoveEvent() {
        Collection<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile());
        GameEvent event = GameEvent.moveEvent(this, tiles, Direction.DOWN);
        assertEquals(GameEvent.EventType.MOVE, event.getType());
        assertEquals(tiles, event.getPushedTiles());
        assertEquals(Direction.DOWN, event.getDirection());
    }
}
