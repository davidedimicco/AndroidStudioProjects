package com.davidedimicco.hypertris;

import java.util.ArrayList;
import java.util.HashMap;

/**Instances of this class represent games of HyperTris
 *
 */
public class HyperTrisModel {

    Player p1;
    Player p2;
    Board board;


    public HyperTrisModel(Player player1, Player player2){
        this.p1=player1;
        this.p2=player2;
        this.board=new Board();
    }

    public void changePlayingOrder(){
        Player temp=this.p1;
        this.p1=this.p2;
        this.p2=temp;
    }

    public void reset(){
        p1.reset();
        p2.reset();
        board.reset();
        if(Math.random()<0.5){
            changePlayingOrder();
        }
    }

    public static void giveReward(Board board,Player p1,Player p2){
        //First player is winner
        if(board.winner==1){
            p1.feedReward(1);
            p2.feedReward(0);
        }
        //Second player is winner
        else if(board.winner==-1){
            p1.feedReward(0);
            p2.feedReward(1);
        }
        //The game ended in a tie
        else{
            p1.feedReward(0.5);
            p2.feedReward(0.5);
        }
    }

    //Forse dovrei rendere questo metodo statico e definire all'interno 2 giocatori intelligenti caricandogli la strategia serializzata in precedenza
    public static void train(HashMap<String,Double> strategy, int n){
        Board board=new Board();
        IntelligentPlayer p1=new IntelligentPlayer(strategy,0.3);
        IntelligentPlayer p2=new IntelligentPlayer(strategy,0.3);

        Position nextGridPosition=null;
        HyperPosition action;
        for(int i=0;i<n;i++){
            while(board.winner==null){
                if(board.oddTurn){
                    action=p1.chooseAction(board.availableHyperPositions(nextGridPosition),board);
                    board.update(action);
                    p1.appendState(board.toString());
                }
                else{
                    action=p2.chooseAction(board.availableHyperPositions(nextGridPosition),board);
                    board.update(action);
                    p2.appendState(board.toString());
                }
                nextGridPosition=action.inGridPosition;
            }
            //Qua devo dare la reward a chi se la merita..
            giveReward(board,p1,p2);
            board.reset();
            p1.reset();
            p2.reset();
        }
        //Here I print the size of the strategy, which is continuously updated throughout the training
        //System.out.println(p1.stateToValue.size());
    }

    public void play(MainActivity view) {
        Position nextGridPosition=null;
        HyperPosition action=null;
        ArrayList<HyperPosition> availableSpots=board.availableHyperPositions(nextGridPosition);
        view.updateView(action,availableSpots);
        while(board.winner==null){
            if(board.oddTurn){
                action=p1.chooseAction(availableSpots,board);
                p1.appendState(board.toString());
            }
            else{
                action=p2.chooseAction(availableSpots,board);
                p2.appendState(board.toString());
            }
            //Here I update the view
            view.updateView(action,availableSpots);
            //Here I update the board
            board.update(action);

            nextGridPosition=action.inGridPosition;
            availableSpots=board.availableHyperPositions(nextGridPosition);
        }
        //Here I give the rewards
        giveReward(board,p1,p2);
    }
}


