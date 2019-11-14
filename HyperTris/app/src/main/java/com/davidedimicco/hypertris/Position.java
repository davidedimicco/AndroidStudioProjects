package com.davidedimicco.hypertris;

import java.util.Objects;

/**Instances of this class are couples (i,j) representing positions in a grid*/
public class Position {

    /**The row index*/
    int x;
    /**The column index*/
    int y;

    /**Constructor
     *
     * @param row The row index
     * @param column The column index
     */
    public Position(int row,int column){
        this.x=row;
        this.y=column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return x+y;
    }
}
