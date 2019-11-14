package com.davidedimicco.hypertris;

import java.util.ArrayList;

/**Instances of this class represent games of HyperTris
 *
 */
public class Game {

    IntelligentPlayer p1;
    IntelligentPlayer p2;
    Board board;


    public Game(IntelligentPlayer player1,IntelligentPlayer player2){
        this.p1=player1;
        this.p2=player2;
        this.board=new Board();
    }

    public void giveReward(){
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
    public void train(int n){

        //Qua dovrei caricare la strategia
        Position nextGridPosition=null;
        HyperPosition action;
        for(int i=0;i<n;i++){
            //while(!board.isOver){
            while(board.winner==null){
                if(board.oddTurn){
                    action=p1.chooseAction(board.availableHyperPositions(nextGridPosition),board);
                    board.update(action);
                    p1.states.add(board.toString());
                }
                else{
                    action=p2.chooseAction(board.availableHyperPositions(nextGridPosition),board);
                    board.update(action);
                    p2.states.add(board.toString());
                }
                nextGridPosition=action.inGridPosition;
            }
            //Qua devo dare la reward a chi se la merita..
            giveReward();
            board.reset();
            p1.reset();
            p2.reset();
        }
        //Qua dovrei salvare la strategia
    }

}
