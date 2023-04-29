package com.davidedimicco.hypertris;

import java.util.Objects;

/**Instances of this class are couples (p_1,p_2) of positions representing positions in a Board*/
public class HyperPosition {

    /**The position of the grid in the board*/
    Position grid;
    /**The position in the grid*/
    Position inGridPosition;

    /**Constructor
     *
     * @param grid The position of the grid in the Board
     * @param position The position in the small grid
     */
    public HyperPosition(Position grid,Position position){
        this.grid=grid;
        this.inGridPosition=position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HyperPosition that = (HyperPosition) o;
        return grid.equals(that.grid) && inGridPosition.equals(that.inGridPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grid, inGridPosition);
    }
}
