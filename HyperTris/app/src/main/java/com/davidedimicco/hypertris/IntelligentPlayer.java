package com.davidedimicco.hypertris;

import java.util.ArrayList;
import java.util.HashMap;

/**The instanced of this class represent players of HyperTris
 *
 */
public class IntelligentPlayer extends Player{

    double learnRate;
    double decayGamma;
    double expRate;

    /**This is a map associating to hash strings of states of the board, their value*/
    HashMap<String,Double> stateToValue;

    /**This is the list of the hash strings representing the states of the board after each move of this player*/
    ArrayList<String> states;

    public IntelligentPlayer(double er){
        learnRate=0.2;
        decayGamma = 0.9;
        this.expRate=er; //0.3
        this.states=new ArrayList<String>();
        stateToValue = new HashMap<String,Double>();
    }

    public IntelligentPlayer(HashMap<String,Double> strategy, double er){
        learnRate=0.2;
        decayGamma = 0.9;
        this.expRate=er; //0.3
        this.states=new ArrayList<String>();
        stateToValue = strategy;
    }

    public void reset(){
        this.states=new ArrayList<String>();
    }

    //This method backpropagate and update states value at the end of game
    public void feedReward(double reward){
        double newValue;
        for (int i=this.states.size()-1;i>=0;i--){
            if (this.stateToValue.get(this.states.get(i))==null){
                this.stateToValue.put(this.states.get(i),0.0);
            }
            newValue=this.stateToValue.get(this.states.get(i));
            newValue+=this.learnRate*(this.decayGamma*reward-newValue);
            this.stateToValue.put(this.states.get(i),newValue);
            reward = newValue;
        }
    }

    public HyperPosition chooseAction(ArrayList<HyperPosition> availablePositions,Board currentBoard){
        if (Math.random()<=this.expRate){
            //Take random action
            return availablePositions.get((int)(availablePositions.size()*Math.random()));
        }
        else{
            HyperPosition bestChoice=availablePositions.get(0);
            double maxValue=-999;
            double value;
            Board nextBoard;
            for (int i=0;i<availablePositions.size();i++){
                nextBoard=currentBoard.copy();
                nextBoard.update(availablePositions.get(i));
                String nextHash=nextBoard.toString();
                if(stateToValue.containsKey(nextHash)){
                    value=stateToValue.get(nextHash);
                }
                else{
                    value=0;
                }
                if(value>maxValue){
                    maxValue=value;
                    bestChoice=availablePositions.get(i);
                }
            }
            return bestChoice;
        }
    }
/*
    public void loadStrategy(){

    }

    public void saveStrategy(){

    }
*/
}
