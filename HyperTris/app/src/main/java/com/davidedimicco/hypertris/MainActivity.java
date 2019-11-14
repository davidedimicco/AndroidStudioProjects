package com.davidedimicco.hypertris;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String,Double> strategy;

    boolean firstOrNot;
    boolean isTheGameStarted;
    boolean isNextGridOk;
    Button resetBtn;
    Button[][][][] btn;
    GridLayout[][] bigGrid;
    String[][] tris;
    public static final int[][] WC1={{0,0},{0,1},{0,2}};
    public static final int[][] WC2={{1,0},{1,1},{1,2}};
    public static final int[][] WC3={{2,0},{2,1},{2,2}};
    public static final int[][] WC4={{0,0},{1,0},{2,0}};
    public static final int[][] WC5={{0,1},{1,1},{2,1}};
    public static final int[][] WC6={{0,2},{1,2},{2,2}};
    public static final int[][] WC7={{0,0},{1,1},{2,2}};
    public static final int[][] WC8={{0,2},{1,1},{2,0}};
    int[][][] WINNING_CONFIGURATIONS={WC1,WC2,WC3,WC4,WC5,WC6,WC7,WC8};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstOrNot=true;
        isTheGameStarted=false;
        isNextGridOk=true;
        //creo il bottone reset e gli assegno un clickListener
        resetBtn=(Button)findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstOrNot=true;
                isTheGameStarted=false;
                for(int i1=0;i1<3;i1++){
                    for(int i2=0;i2<3;i2++){
                        for(int i3=0;i3<3;i3++){
                            for(int i4=0;i4<3;i4++){
                                btn[i1][i2][i3][i4].setText("");
                            }
                        }
                    }
                }
                for(int i1=0;i1<3;i1++){
                    for(int i2=0;i2<3;i2++){
                        bigGrid[i1][i2].setBackgroundColor(Color.parseColor("#9E9E9E"));
                    }
                }
                for(int i1=0;i1<3;i1++){
                    for(int i2=0;i2<3;i2++){
                        tris[i1][i2]="";
                    }
                }
            }
        });
        //creo il vettore di bottoni e assegno a tutti lo stesso clickListener
        btn=new Button[3][3][3][3];
        btn[0][0][0][0]=(Button)findViewById(R.id.btn0000);
        btn[0][0][0][1]=(Button)findViewById(R.id.btn0001);
        btn[0][0][0][2]=(Button)findViewById(R.id.btn0002);
        btn[0][0][1][0]=(Button)findViewById(R.id.btn0010);
        btn[0][0][1][1]=(Button)findViewById(R.id.btn0011);
        btn[0][0][1][2]=(Button)findViewById(R.id.btn0012);
        btn[0][0][2][0]=(Button)findViewById(R.id.btn0020);
        btn[0][0][2][1]=(Button)findViewById(R.id.btn0021);
        btn[0][0][2][2]=(Button)findViewById(R.id.btn0022);
        btn[0][1][0][0]=(Button)findViewById(R.id.btn0100);
        btn[0][1][0][1]=(Button)findViewById(R.id.btn0101);
        btn[0][1][0][2]=(Button)findViewById(R.id.btn0102);
        btn[0][1][1][0]=(Button)findViewById(R.id.btn0110);
        btn[0][1][1][1]=(Button)findViewById(R.id.btn0111);
        btn[0][1][1][2]=(Button)findViewById(R.id.btn0112);
        btn[0][1][2][0]=(Button)findViewById(R.id.btn0120);
        btn[0][1][2][1]=(Button)findViewById(R.id.btn0121);
        btn[0][1][2][2]=(Button)findViewById(R.id.btn0122);
        btn[0][2][0][0]=(Button)findViewById(R.id.btn0200);
        btn[0][2][0][1]=(Button)findViewById(R.id.btn0201);
        btn[0][2][0][2]=(Button)findViewById(R.id.btn0202);
        btn[0][2][1][0]=(Button)findViewById(R.id.btn0210);
        btn[0][2][1][1]=(Button)findViewById(R.id.btn0211);
        btn[0][2][1][2]=(Button)findViewById(R.id.btn0212);
        btn[0][2][2][0]=(Button)findViewById(R.id.btn0220);
        btn[0][2][2][1]=(Button)findViewById(R.id.btn0221);
        btn[0][2][2][2]=(Button)findViewById(R.id.btn0222);
        btn[1][0][0][0]=(Button)findViewById(R.id.btn1000);
        btn[1][0][0][1]=(Button)findViewById(R.id.btn1001);
        btn[1][0][0][2]=(Button)findViewById(R.id.btn1002);
        btn[1][0][1][0]=(Button)findViewById(R.id.btn1010);
        btn[1][0][1][1]=(Button)findViewById(R.id.btn1011);
        btn[1][0][1][2]=(Button)findViewById(R.id.btn1012);
        btn[1][0][2][0]=(Button)findViewById(R.id.btn1020);
        btn[1][0][2][1]=(Button)findViewById(R.id.btn1021);
        btn[1][0][2][2]=(Button)findViewById(R.id.btn1022);
        btn[1][1][0][0]=(Button)findViewById(R.id.btn1100);
        btn[1][1][0][1]=(Button)findViewById(R.id.btn1101);
        btn[1][1][0][2]=(Button)findViewById(R.id.btn1102);
        btn[1][1][1][0]=(Button)findViewById(R.id.btn1110);
        btn[1][1][1][1]=(Button)findViewById(R.id.btn1111);
        btn[1][1][1][2]=(Button)findViewById(R.id.btn1112);
        btn[1][1][2][0]=(Button)findViewById(R.id.btn1120);
        btn[1][1][2][1]=(Button)findViewById(R.id.btn1121);
        btn[1][1][2][2]=(Button)findViewById(R.id.btn1122);
        btn[1][2][0][0]=(Button)findViewById(R.id.btn1200);
        btn[1][2][0][1]=(Button)findViewById(R.id.btn1201);
        btn[1][2][0][2]=(Button)findViewById(R.id.btn1202);
        btn[1][2][1][0]=(Button)findViewById(R.id.btn1210);
        btn[1][2][1][1]=(Button)findViewById(R.id.btn1211);
        btn[1][2][1][2]=(Button)findViewById(R.id.btn1212);
        btn[1][2][2][0]=(Button)findViewById(R.id.btn1220);
        btn[1][2][2][1]=(Button)findViewById(R.id.btn1221);
        btn[1][2][2][2]=(Button)findViewById(R.id.btn1222);
        btn[2][0][0][0]=(Button)findViewById(R.id.btn2000);
        btn[2][0][0][1]=(Button)findViewById(R.id.btn2001);
        btn[2][0][0][2]=(Button)findViewById(R.id.btn2002);
        btn[2][0][1][0]=(Button)findViewById(R.id.btn2010);
        btn[2][0][1][1]=(Button)findViewById(R.id.btn2011);
        btn[2][0][1][2]=(Button)findViewById(R.id.btn2012);
        btn[2][0][2][0]=(Button)findViewById(R.id.btn2020);
        btn[2][0][2][1]=(Button)findViewById(R.id.btn2021);
        btn[2][0][2][2]=(Button)findViewById(R.id.btn2022);
        btn[2][1][0][0]=(Button)findViewById(R.id.btn2100);
        btn[2][1][0][1]=(Button)findViewById(R.id.btn2101);
        btn[2][1][0][2]=(Button)findViewById(R.id.btn2102);
        btn[2][1][1][0]=(Button)findViewById(R.id.btn2110);
        btn[2][1][1][1]=(Button)findViewById(R.id.btn2111);
        btn[2][1][1][2]=(Button)findViewById(R.id.btn2112);
        btn[2][1][2][0]=(Button)findViewById(R.id.btn2120);
        btn[2][1][2][1]=(Button)findViewById(R.id.btn2121);
        btn[2][1][2][2]=(Button)findViewById(R.id.btn2122);
        btn[2][2][0][0]=(Button)findViewById(R.id.btn2200);
        btn[2][2][0][1]=(Button)findViewById(R.id.btn2201);
        btn[2][2][0][2]=(Button)findViewById(R.id.btn2202);
        btn[2][2][1][0]=(Button)findViewById(R.id.btn2210);
        btn[2][2][1][1]=(Button)findViewById(R.id.btn2211);
        btn[2][2][1][2]=(Button)findViewById(R.id.btn2212);
        btn[2][2][2][0]=(Button)findViewById(R.id.btn2220);
        btn[2][2][2][1]=(Button)findViewById(R.id.btn2221);
        btn[2][2][2][2]=(Button)findViewById(R.id.btn2222);
        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn=(Button)v;
                if(btn.getText().toString().equals("?") || !isTheGameStarted){
                    if(!isTheGameStarted){
                        isTheGameStarted=true;
                    }
                    btn.setTextColor(firstOrNot? Color.BLUE : Color.RED);
                    btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f);
                    btn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    btn.setText(firstOrNot? "X" : "O");
                    firstOrNot=!firstOrNot;
                    GridLayout grid=(GridLayout)v.getParent();
                    try {
                        checkPartialVictory(grid,btn);
                    }catch (IllegalArgumentException e){
                    }
                }
            }
        };
        for(int i1=0;i1<3;i1++){
            for(int i2=0;i2<3;i2++){
                for(int i3=0;i3<3;i3++){
                    for(int i4=0;i4<3;i4++){
                        btn[i1][i2][i3][i4].setOnClickListener(listener);
                    }
                }
            }
        }
        //creo la matrice 3x3 di griglie
        bigGrid = new GridLayout[3][3];
        bigGrid[0][0]=(GridLayout)findViewById(R.id.grid00);
        bigGrid[0][1]=(GridLayout)findViewById(R.id.grid01);
        bigGrid[0][2]=(GridLayout)findViewById(R.id.grid02);
        bigGrid[1][0]=(GridLayout)findViewById(R.id.grid10);
        bigGrid[1][1]=(GridLayout)findViewById(R.id.grid11);
        bigGrid[1][2]=(GridLayout)findViewById(R.id.grid12);
        bigGrid[2][0]=(GridLayout)findViewById(R.id.grid20);
        bigGrid[2][1]=(GridLayout)findViewById(R.id.grid21);
        bigGrid[2][2]=(GridLayout)findViewById(R.id.grid22);
        //creo la matrice 3x3 di stringhe e le pongo tutte uguali alla stringa vuota
        tris=new String[3][3];
        for(int i1=0;i1<3;i1++){
            for(int i2=0;i2<3;i2++){
                tris[i1][i2]="";
            }
        }
    }
    public void checkPartialVictory(GridLayout grid, Button button) throws IllegalArgumentException{
        ArrayList<Integer> gridIndices=this.getGridIndices(grid);
        int i1=gridIndices.get(0);
        int i2=gridIndices.get(1);
        deleteOldQuestionMarks(button);
        if(!tris[i1][i2].equals("")){
            writeNewQuestionMarks(button);
            return;
        }
        String temp="";
        boolean isThereATris=true;
        for(int i=0;i<8;i++){
            isThereATris=true;
            temp="";
            for(int j=0;j<3;j++){
                if(btn[i1][i2][WINNING_CONFIGURATIONS[i][j][0]][WINNING_CONFIGURATIONS[i][j][1]].getText().toString().equals("")){
                    isThereATris=false;
                    break;
                }
                else{
                    if(temp.equals("")){
                        temp=btn[i1][i2][WINNING_CONFIGURATIONS[i][j][0]][WINNING_CONFIGURATIONS[i][j][1]].getText().toString();
                    }
                    else{
                        if(!temp.equals(btn[i1][i2][WINNING_CONFIGURATIONS[i][j][0]][WINNING_CONFIGURATIONS[i][j][1]].getText().toString())){
                            isThereATris=false;
                            break;
                        }
                    }
                }
            }
            if(isThereATris){
                break;
            }
        }
        if(isThereATris){
            this.tris[i1][i2]=temp;
            grid.setBackgroundColor(temp.equals("X")? Color.BLUE : Color.RED);
        }
        if(gameOver()){
            //Qua quando uno ha vinto
            return;
        }
        else{
            writeNewQuestionMarks(button);
        }
    }

    public boolean gameOver(){
        String temp="";
        boolean isGameOver=true;
        for(int i=0;i<8;i++){
            isGameOver=true;
            temp="";
            for(int j=0;j<3;j++){
                if(tris[WINNING_CONFIGURATIONS[i][j][0]][WINNING_CONFIGURATIONS[i][j][1]].equals("")){
                    isGameOver=false;
                    break;
                }
                else{
                    if(temp.equals("")){
                        temp=tris[WINNING_CONFIGURATIONS[i][j][0]][WINNING_CONFIGURATIONS[i][j][1]];
                    }
                    else{
                        if(!temp.equals(tris[WINNING_CONFIGURATIONS[i][j][0]][WINNING_CONFIGURATIONS[i][j][1]])){
                            isGameOver=false;
                            break;
                        }
                    }
                }
            }
            if(isGameOver){
                break;
            }
        }
        if(isGameOver){
            Toast toast = Toast.makeText(this,"The winner is Player "+temp+"!",Toast.LENGTH_LONG);
            toast.show();
        }
        return isGameOver;
    }
    public void deleteOldQuestionMarks(Button button){
        ArrayList<Integer> indices=getButtonIndices(button);
        //cancello i punti di domanda nella griglia dove è stato inserito il nuovo simbolo, nel caso in cui la mossa precedente fosse limitata ad una griglia
        if(isNextGridOk) {
            for (int i1 = 0; i1 < 3; i1++) {
                for (int i2 = 0; i2 < 3; i2++) {
                    if (btn[indices.get(0)][indices.get(1)][i1][i2].getText().toString().equals("?")) {
                        btn[indices.get(0)][indices.get(1)][i1][i2].setText("");
                    }
                }
            }
        }
        //cancello i punti di domanda in tutta la griglia grande perchè l'ultima mossa era una mossa non limitata ad una griglia
        else{
            for(int i1=0;i1<3;i1++){
                for(int i2=0;i2<3;i2++){
                    for(int i3=0;i3<3;i3++){
                        for(int i4=0;i4<3;i4++){
                            if(btn[i1][i2][i3][i4].getText().toString().equals("?")){
                                btn[i1][i2][i3][i4].setText("");
                            }
                        }
                    }
                }
            }
        }
    }

    public void writeNewQuestionMarks(Button button){
        ArrayList<Integer> indices=getButtonIndices(button);
        isNextGridOk=false;
        //metto un "?" nero in quelli della futura griglia e che non hanno un simbolo (SE QUESTO È POSSIBILE!)
        for(int i1=0;i1<3;i1++){
            for(int i2=0;i2<3;i2++){
                if(btn[indices.get(2)][indices.get(3)][i1][i2].getText().toString().equals("")){
                    btn[indices.get(2)][indices.get(3)][i1][i2].setTextColor(Color.BLACK);
                    btn[indices.get(2)][indices.get(3)][i1][i2].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                    btn[indices.get(2)][indices.get(3)][i1][i2].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    btn[indices.get(2)][indices.get(3)][i1][i2].setText("?");
                    isNextGridOk=true;
                }
            }
        }
        //Vecchia strategia: metto un "?" nero in quelli delLa griglia attuale che non hanno un simbolo
        /*
        if(!isNextGridOk){
            for(int i1=0;i1<3;i1++){
                for(int i2=0;i2<3;i2++){
                    if(btn[indices.get(0)][indices.get(1)][i1][i2].getText().toString().equals("")){
                        btn[indices.get(0)][indices.get(1)][i1][i2].setTextColor(Color.BLACK);
                        btn[indices.get(0)][indices.get(1)][i1][i2].setText("?");
                    }
                }
            }
        }
        */
        //metto un "?" nero in tutti i posti disponibili se il giocatore prima mi ha mandato in una griglia piena
        //Anche perchè la tattica di lasciarti nella griglia attuale non risolve tutti i bug (ad esempio posso usare l'ultima casella libera di una griglia per mandarti in una griglia completa)
        if(!isNextGridOk){
            for(int i1=0;i1<3;i1++){
                for(int i2=0;i2<3;i2++){
                    for(int i3=0;i3<3;i3++){
                        for(int i4=0;i4<3;i4++){
                            if(btn[i1][i2][i3][i4].getText().toString().equals("")){
                                btn[i1][i2][i3][i4].setTextColor(Color.BLACK);
                                btn[i1][i2][i3][i4].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                                btn[i1][i2][i3][i4].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                                btn[i1][i2][i3][i4].setText("?");
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Integer> getButtonIndices(Button button){
        for(int i1=0;i1<3;i1++){
            for(int i2=0;i2<3;i2++){
                for(int i3=0;i3<3;i3++){
                    for(int i4=0;i4<3;i4++){
                        if(button.equals(btn[i1][i2][i3][i4])){
                            ArrayList<Integer> indices=new ArrayList<Integer>();
                            indices.add(i1);
                            indices.add(i2);
                            indices.add(i3);
                            indices.add(i4);
                            return indices;
                        }
                    }
                }
            }
        }
        //Di qua in teoria non passa mai, ma magari andrebbe sistemato facendo lanciare un eccezione
        return null;
    }

    public ArrayList<Integer> getGridIndices(GridLayout grid){
        for(int i1=0;i1<3;i1++){
            for(int i2=0;i2<3;i2++){
                if(grid.equals(bigGrid[i1][i2])){
                    ArrayList<Integer> indices=new ArrayList<Integer>();
                    indices.add(i1);
                    indices.add(i2);
                    return indices;
                }
            }
        }
        //Di qua in teoria non passa mai, ma magari andrebbe sistemato facendo lanciare un eccezione
        return null;
    }


    

    public void deserializeStrategy(){
        try {
            FileInputStream fis=this.openFileInput("Strategy");
            ObjectInputStream ois=new ObjectInputStream(fis);
            this.strategy=(HashMap<String,Double>)ois.readObject();
            ois.close();
            fis.close();
            //Toast toast = Toast.makeText(this, "ARCHIVE FOUND!", Toast.LENGTH_LONG);
            //toast.show();
        } catch (ClassNotFoundException | IOException e1) {
            //se la strategia ancora non esiste la creo
            strategy = new HashMap<String,Double>();
            //Toast toast = Toast.makeText(this, "Archive file not found!", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void serializeStrategy(){
        FileOutputStream fos;
        try {
            //fos = new FileOutputStream(((MainActivity)getActivity()).path+((MainActivity)getActivity()).startingLanguage+"-"+((MainActivity)getActivity()).endingLanguage);
            fos = this.openFileOutput("Strategy", Context.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            //QUA DEVO INSERIRE LA STRATEGIA DA SERIALIZZARE AL POSTO DELL'ARCHIVIO
            oos.writeObject(this.strategy);
            oos.close();
            fos.close();
            //Toast toast = Toast.makeText(this, "ARCHIVE WRITTEN", Toast.LENGTH_LONG);
            //toast.show();
        } catch (IOException e) {
            //Toast toast = Toast.makeText(this,"Archive file not written", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void giveReward(Player p1,Player p2, Board board){
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

    public void train(int n){
        //Here I create two IntelligentPlayers with the saved strategy
        deserializeStrategy();
        IntelligentPlayer p1=new IntelligentPlayer(strategy,0.3);
        IntelligentPlayer p2=new IntelligentPlayer(strategy,0.3);

        Board board=new Board();
        Position nextGridPosition=null;
        HyperPosition action;
        for(int i=0;i<n;i++){
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
            giveReward(p1,p2,board);
            board.reset();
            p1.reset();
            p2.reset();
        }
        //Here I save the strategy (DOES THIS REALLY WORK OR THE ONLY MODIFICATION IS IN THE INPUT OF THE INTELLIGENTPLAYERS?)
        serializeStrategy();
    }

}