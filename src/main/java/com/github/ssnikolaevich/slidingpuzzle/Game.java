package com.github.ssnikolaevich.slidingpuzzle;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;

public class Game {
    private Collection<Tile> tiles;
    private int columns;
    private int rows;
    private String gliph;
    private Collection<GameListener> listeners;

    public Game(int columns, int rows, String gliph, Collection<Tile> tiles) {
        this.columns = columns;
        this.rows = rows;
        this.gliph = gliph;
        this.tiles = tiles;
    }
    
    public Game(Element element) {
        columns = Integer.decode(element.getAttribute("columns"));
        rows = Integer.decode(element.getAttribute("rows"));
        gliph = element.getAttribute("gliph");
        tiles = new ArrayList<>();
        NodeList nodes = element.getElementsByTagName("tile");
        for (int i = 0; i < nodes.getLength(); ++i) {
            Tile tile = new Tile((Element)nodes.item(i));
            tiles.add(tile);
        }
        listeners = new HashSet<>();
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public String getGliph() {
        return gliph;
    }

    public Collection<Tile> getTiles() {
        return tiles;
    }

    public void addListener(GameListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }

    private void processGameEvent(GameEvent event) {
        for (GameListener listener : listeners) {
            listener.handle(event);
        }
    }
    
    public boolean isOver() {
        for (Tile tile : tiles) {
            if (tile.getPosition() != tile.getOrigin()) {
                return false;
            }
        }
        return true;
    }
    
    public Tile getTileAt(int column, int row) {
        for (Tile tile : tiles) {
            final Position position = tile.getPosition();
            final int c = position.getColumn();
            final int r = position.getRow();
            if (
                (column >= c)
                && (column <= (c + tile.getColumns()))
                && (row >= r)
                && (row <= (r + tile.getRows()))
            ) {
                return tile;
            }
        }
        return null;
    }

    private Rectangle getPushRectangle(Tile tile, Direction direction) {
        final Position position = tile.getPosition();
        int left = position.getColumn();
        int top = position.getRow();
        int right = left + tile.getColumns();
        int bottom = top + tile.getRows();
        switch (direction) {
            case LEFT: {
                left -= 1;
                if (left < 0) {
                    left = columns - 1;
                }
                right = left;
                break;
            }
            case RIGHT: {
                left = right;
                if (left >= columns) {
                    left = 0;
                }
                right = left;
                break;
            }
            case UP: {
                top -= 1;
                if (top < 0) {
                    top = rows - 1;
                }
                bottom = top;
                break;
            }
            case DOWN: {
                top = bottom;
                if (top >= rows) {
                    top = 0;
                }
                bottom = top;
                break;
            }
        }
        return new Rectangle(left, top, right, bottom);
    }

    private boolean pushes(Tile activeTile, Tile tile, Direction direction) {
        final Rectangle pushRectangle = getPushRectangle(activeTile, direction);
        return tile.getRectangle().intersectsWith(pushRectangle);

    }
    
    private Collection<Tile> getPushedTiles(int column, int row, Direction direction) {
        Collection<Tile> pushedTiles = new HashSet<>();
        Collection<Tile> activeTiles = new HashSet<>();
        Tile tile = getTileAt(column, row);
        if (tile != null) {
            activeTiles.add(tile);
            pushedTiles.add(tile);
        }
        while (!activeTiles.isEmpty()) {
            Collection<Tile> next = new HashSet<>();
            for (Tile t : tiles) {
                if (!pushedTiles.contains(t)) {
                    for (Tile activeTile : activeTiles) {
                        if (pushes(activeTile, t, direction)) {
                            next.add(t);
                        }
                    }
                }
            }
            pushedTiles.addAll(next);
            activeTiles = next;
        }
        return pushedTiles;
    }

    private boolean moveEnabled(Tile tile, Direction direction) {
        final Position position = tile.getPosition();
        final int cmin = position.getColumn();
        final int rmin = position.getRow();
        final int tileColumns = tile.getColumns();
        final int tileRows = tile.getRows();
        final int cmax = cmin + tileColumns;
        final int rmax = rmin + tileRows;
        return !(
                tile.isFixed()
                || ((direction == Direction.LEFT) && (cmin == 0) && (tileColumns > 1))
                || ((direction == Direction.UP) && (rmin == 0) && (tileRows > 1))
                || ((direction == Direction.RIGHT) && (cmax == (columns - 1)) && (tileColumns > 1))
                || ((direction == Direction.DOWN) && (rmax == (rows - 1)) && (tileRows > 1))
        );
    }
    
    private boolean moveEnabled(Collection<Tile> tiles, Direction direction) {
        for (Tile tile : tiles) {
            if (!moveEnabled(tile, direction)) {
                return false;
            }
        }
        return true;
    }
    
    private void moveTile(Tile tile, Direction direction) {
        Position position = tile.getPosition();
        int column = position.getColumn();
        int row = position.getRow();
        switch (direction) {
            case RIGHT: {
                    ++column;
                    column = (column + 1) / columns;
                    break;
                }
            case DOWN: {
                    row = (row + 1) / rows;
                    break;
                }
            case LEFT: {
                    column = ((column > 0)? column : columns) - 1;
                    break;
                }
            case UP: {
                    row = ((row > 0)? row : rows) - 1;
                    break;
                }
        }
        position.setColumn(column);
        position.setRow(row);
    }
    
    private void moveTiles(Collection<Tile> movingTiles, Direction direction) {
        for (Tile tile : movingTiles) {
            moveTile(tile, direction);
        }
    }
    
    public void makeMove(int column, int row, Direction direction) {
        final Collection<Tile> pushedTiles = getPushedTiles(column, row, direction);
        if (moveEnabled(pushedTiles, direction)) {
            processGameEvent(GameEvent.moveEvent(this, pushedTiles, direction));
            moveTiles(pushedTiles, direction);
            if (isOver()) {
                processGameEvent(GameEvent.gameOverEvent(this));
            }
        }
    }
}
