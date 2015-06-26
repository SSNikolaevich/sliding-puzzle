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
