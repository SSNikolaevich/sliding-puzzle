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
