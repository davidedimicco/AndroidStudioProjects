package com.davidedimicco.hypertris;

import java.util.ArrayList;

public class HumanPlayer extends Player {

    /**This is the list of the hash strings representing the states of the board after each move of this player*/
    private ArrayList<String> states;

    HyperPosition chosenAction;

    public HumanPlayer(){
        this.states=new ArrayList<String>();
        this.chosenAction=null;
    }

    public void appendState(String stateHash){
        //For the moment this method is empty. Later If I add a "Back" button on the view, I need to save the strings representing the states and to recover from the last state string, the previous position of the board to rebuild it
    }

    //This has to be checked!
    public HyperPosition chooseAction(ArrayList<HyperPosition> availablePositions, Board currentBoard){
        while(chosenAction==null){
            //Wait for a button to be clicked
        }
        HyperPosition action=chosenAction;
        chosenAction=null;
        return action;
    }

    //This has to be done!
    public void reset(){
    }

    public void feedReward(double reward){
        //Do nothing
    }

}
