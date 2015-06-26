package com.github.ssnikolaevich.slidingpuzzle;

import org.w3c.dom.Element;

public class Position {
    private int column;
    private int row;
    
    public Position() {
        column = 0;
        row = 0;
    }
    
    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position(Element element) {
        column = Integer.decode(element.getAttribute("column"));
        row = Integer.decode(element.getAttribute("row"));
    }
    
    public int getColumn() {
        return column;
    }
    
    public int getRow() {
        return row;
    }
    
    public void setColumn(int column) {
        this.column = column;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
}
