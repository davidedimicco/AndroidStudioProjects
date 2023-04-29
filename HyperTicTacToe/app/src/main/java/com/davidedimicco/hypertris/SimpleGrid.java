package com.davidedimicco.hypertris;

import java.util.ArrayList;
import java.util.Arrays;

/**Instances of this class are Tic Tac Toe grids
 *
 */
public class SimpleGrid {

    /**The grid of intereger representing the tic tac toe grid*/
    int[][] grid;

    /**Constructor
     *
     */
    public SimpleGrid(){
        this.grid=new int[3][3];
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                this.grid[i][j]=0;
            }
        }
    }


    public SimpleGrid copy(){
        SimpleGrid copy=new SimpleGrid();
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                copy.grid[i][j]=this.grid[i][j];
            }
        }
        return copy;
    }

    /**This method reset the grid to the initial state
     *
     */
    public void reset(){
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                this.grid[i][j]=0;
            }
        }
    }

    /**This method returns the array of free Positions in the grid
     *
     * @return the array of free Positions in the grid
     */
    public ArrayList<Position> getAvailablePositions(){
        ArrayList<Position> freeSpots=new ArrayList<Position>();
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if(this.grid[i][j]==0){
                    freeSpots.add(new Position(i,j));
                }
            }
        }
        return freeSpots;
    }

    /**This method check if the grid is already full
     *
     * @return true iff there is no space left
     */
    public boolean isFull(){
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if(this.grid[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }

    /**This method check if there is a winner and return the outcome
     *
     * @return 1 if the winner is the first player, -1 if the winner is the second player and 0 if there is no winner
     */
    public int checkPartialWinner(){
        int sum;
        //For rows
        for (int i=0;i<3;i++){
            sum=0;
            for (int j=0;j<3;j++){
                sum+=this.grid[i][j];
            }
            if(sum==3){
                return 1;
            }
            if(sum==-3){
                return -1;
            }
        }
        //For columns
        for (int i=0;i<3;i++){
            sum=0;
            for (int j=0;j<3;j++){
                sum+=this.grid[j][i];
            }
            if(sum==3){
                return 1;
            }
            if(sum==-3){
                return -1;
            }
        }
        //For diagonals
        sum=0;
        for(int i=0;i<3;i++){
            sum+=this.grid[i][i];
        }
        if(sum==3){
            return 1;
        }
        if(sum==-3) {
            return -1;
        }
        sum=0;
        for(int i=0;i<3;i++){
            sum+=this.grid[i][2-i];
        }
        if(sum==3) {
            return 1;
        }
        if(sum==-3) {
            return -1;
        }
        //For the tie
        if(isFull()){
            //8 stands for a tie (being a mix of a X and O)
            return 8;
        }
        //Otherwise
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleGrid that = (SimpleGrid) o;
        return Arrays.equals(grid, that.grid);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(grid);
    }

    @Override
    public String toString() {
        String s="";
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                s+=this.grid[i][j];
            }
        }
        return s;
    }

}
