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

import java.util.Collection;
import java.util.EventObject;

public class GameEvent extends EventObject {
    public enum EventType {
        GAME_OVER,
        MOVE
    }

    private EventType type;
    private Collection<Tile> pushedTiles;
    private Direction direction;

    private GameEvent(Object source, EventType type, Collection<Tile> pushedTiles, Direction direction) {
        super(source);
        this.type = type;
        this.pushedTiles = pushedTiles;
        this.direction = direction;
    }

    public EventType getType() {
        return type;
    }

    public Collection<Tile> getPushedTiles() {
        return pushedTiles;
    }

    public Direction getDirection() {
        return direction;
    }

    public static GameEvent gameOverEvent(Object source) {
        return new GameEvent(source, EventType.GAME_OVER, null, null);
    }

    public static GameEvent moveEvent(
            Object source,
            Collection<Tile> pushedTiles,
            Direction direction
    ) {
        return new GameEvent(source, EventType.MOVE, pushedTiles, direction);
    }
}
