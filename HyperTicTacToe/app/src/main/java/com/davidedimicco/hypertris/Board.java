package com.davidedimicco.hypertris;

import java.util.ArrayList;
import java.util.Arrays;

/**The instances of this class represent boards of HyperTris
 *
 */
public class Board {

    /**The 3x3 grid of Tic Tac Toe grids*/
    SimpleGrid[][] gridOfGrids;

    /**The grid in which each entry is written by winning a Tic Tac Toe game*/
    SimpleGrid bigGrid;

    boolean oddTurn;

    Integer winner;

    /**This method build a 3x3 grid of new Tic Tac Toe grids
     *
     */
    public Board(){
        this.gridOfGrids=new SimpleGrid[3][3];
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                this.gridOfGrids[i][j]=new SimpleGrid();
            }
        }
        this.bigGrid=new SimpleGrid();
        this.oddTurn=true;
        this.winner=null;
    }

    public int update(HyperPosition hp){
        //Here the Hyperposition has to be free
        int partialWinner;
        (gridOfGrids[hp.grid.x][hp.grid.y]).grid[hp.inGridPosition.x][hp.inGridPosition.y]=(oddTurn? 1:-1);
        if(bigGrid.grid[hp.grid.x][hp.grid.y]==0){
            partialWinner=(gridOfGrids[hp.grid.x][hp.grid.y]).checkPartialWinner();
            if(partialWinner!=0){
                bigGrid.grid[hp.grid.x][hp.grid.y]=partialWinner;
                checkWinner();
                oddTurn=!oddTurn;
                return partialWinner;
            }
        }
        oddTurn=!oddTurn;
        return 0;
    }

    //I could improve this method by passing the last HyperPosition as input and checking only its consequences
    public void checkWinner(){
        int w=bigGrid.checkPartialWinner();
        if(w!=0){
            winner=w;
        }
    }
    
    private ArrayList<HyperPosition> availableHyperPositions(){
        ArrayList<HyperPosition> freeSpots = new ArrayList<HyperPosition>();
        Position grid;
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                grid=new Position(i,j);
                for (Position availablePosition:gridOfGrids[i][j].getAvailablePositions()) {
                    freeSpots.add(new HyperPosition(grid,availablePosition));
                }
            }
        }
        return freeSpots;
    }

    public ArrayList<HyperPosition> availableHyperPositions(Position grid){
        if(grid==null || gridOfGrids[grid.x][grid.y].isFull()){
            return availableHyperPositions();
        }
        ArrayList<HyperPosition> freeSpots = new ArrayList<HyperPosition>();
        for (Position availablePosition:gridOfGrids[grid.x][grid.y].getAvailablePositions()) {
            freeSpots.add(new HyperPosition(grid,availablePosition));
        }
        return freeSpots;
    }

    public Board copy(){
        Board copy=new Board();
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                copy.gridOfGrids[i][j]=this.gridOfGrids[i][j].copy();
            }
        }
        copy.bigGrid=this.bigGrid.copy();
        copy.oddTurn=this.oddTurn;
        copy.winner=this.winner;
        return copy;
    }

    public void reset(){
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                gridOfGrids[i][j].reset();
            }
        }
        bigGrid.reset();
        oddTurn=true;
        winner=null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.equals(gridOfGrids, board.gridOfGrids);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(gridOfGrids);
    }

    @Override
    public String toString() {
        String s=this.bigGrid.toString();
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                s+=this.gridOfGrids[i][j].toString();
            }
        }
        return s;
    }

}
