package com.github.ssnikolaevich.slidingpuzzle;

import org.w3c.dom.Element;

public class Tile {
    private Position origin;
    private Position position;
    private int columns;
    private int rows;
    private boolean fixed;
    
    public Tile() {
        origin = new Position();
        position = new Position();
        rows = 1;
        columns = 1;
        fixed = false;
    }

    public Tile(Element element) {
        origin = new Position((Element)element.getElementsByTagName("origin").item(0));
        position = new Position((Element)element.getElementsByTagName("position").item(0));
        columns = Integer.decode(element.getAttribute("columns"));
        rows = Integer.decode(element.getAttribute("rows"));
        fixed = Integer.decode(element.getAttribute("fixed")) != 0;
    }
    
    public Position getOrigin() {
        return origin;
    }
    
    public void setOrigin(Position origin) {
        this.origin = origin;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public int getRows() {
        return rows;
    }
    
    public void setRows(int rows) {
        this.rows = Math.max(1, rows);
    }
    
    public int getColumns() {
        return columns;
    }
    
    public void setColumns(int columns) {
        this.columns = Math.max(1, columns);
    }
    
    public boolean isFixed() {
        return fixed;
    }
    
    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public Rectangle getRectangle() {
        final int column = position.getColumn();
        final int row = position.getRow();
        return new Rectangle(column, row, column + columns - 1, row + rows - 1);
    }
}
