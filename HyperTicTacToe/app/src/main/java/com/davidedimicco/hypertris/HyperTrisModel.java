package com.davidedimicco.hypertris;

/**Instances of this class represent games of HyperTris
 *
 */
public class HyperTrisModel {

    Board board;

    Position nextGridPosition;

    int partial_winner;

    public HyperTrisModel(){
        this.board=new Board();
        this.nextGridPosition=null;
        partial_winner=0;
    }

    public void reset(){
        board.reset();
        nextGridPosition=null;
        partial_winner=0;
    }
}


