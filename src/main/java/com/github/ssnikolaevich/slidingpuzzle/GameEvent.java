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
