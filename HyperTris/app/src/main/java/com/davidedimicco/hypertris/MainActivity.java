package com.davidedimicco.hypertris;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String,Double> strategy;
    HyperTrisModel model;
    IntelligentPlayer ip;
    HumanPlayer hp;

    boolean isTheGameStarted;
    Button resetBtn;
    Button[][][][] btn;
    GridLayout[][] bigGrid;
    String[][] tris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //NEW STUFF
        deserializeStrategy();
        ip=new IntelligentPlayer(strategy,0.3);
        hp=new HumanPlayer();
        //Here we choose who is the first player
        if(Math.random()<0.5){
            model=new HyperTrisModel(ip,hp);
        }
        else{
            model=new HyperTrisModel(hp,ip);
        }

        //OLD STUFF
        isTheGameStarted=false;
        //creo il bottone reset e gli assegno un clickListener
        resetBtn=(Button)findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.reset();
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
                if((model.board.oddTurn && (model.p1.getClass() == IntelligentPlayer.class))||(!model.board.oddTurn && (model.p2.getClass() == IntelligentPlayer.class))){
                    //Passing here means that there is some mistake, because buttons should not be clickable during IntelligentPlayer's turns
                    return;
                }
                //From here on, we know that the actual player is human
                HumanPlayer actualPlayer;
                if(model.board.oddTurn){
                    actualPlayer=(HumanPlayer)model.p1;
                }
                else{
                    actualPlayer=(HumanPlayer)model.p2;
                }
                Button btn=(Button)v;
                //The following instruction is needed because all the other clickable buttons have a question mark and are set unclickable in the deleteQuestionMarks method
                btn.setClickable(false);
                deleteQuestionMarks();
                ArrayList<Integer> indices=getButtonIndices(btn);
                actualPlayer.chosenAction=new HyperPosition(new Position(indices.get(0),indices.get(1)),new Position(indices.get(2),indices.get(3)));
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

        //model.play(this);
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

    public void updateView(HyperPosition lastMove, ArrayList<HyperPosition> availableHyperPositions){
        //We enter this if only when the player playing in this moment is a HumanPlayer
        if((model.board.oddTurn && model.p1.getClass()==hp.getClass())||(!model.board.oddTurn && model.p2.getClass()==hp.getClass())){
            writeQuestionMarks(availableHyperPositions);
        }
        if(lastMove==null){
            return;
        }
        int x1=lastMove.grid.x,y1=lastMove.grid.y,x2=lastMove.inGridPosition.x,y2=lastMove.inGridPosition.y;
        btn[x1][y1][x2][y2].setTextColor(model.board.oddTurn? Color.BLUE : Color.RED);
        btn[x1][y1][x2][y2].setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f);
        btn[x1][y1][x2][y2].setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        btn[x1][y1][x2][y2].setText(model.board.oddTurn? "X" : "O");


    }

    public void deleteQuestionMarks(){
        for(int i1=0;i1<3;i1++){
            for(int i2=0;i2<3;i2++){
                for(int i3=0;i3<3;i3++){
                    for(int i4=0;i4<3;i4++){
                        if(btn[i1][i2][i3][i4].getText().toString().equals("?")){
                            btn[i1][i2][i3][i4].setClickable(false);
                            btn[i1][i2][i3][i4].setText("");
                        }

                    }
                }
            }
        }
    }

    public void writeQuestionMarks(ArrayList<HyperPosition> availableHyperPositions){
        int x1,y1,x2,y2;
        //Here I draw the question marks and set their buttons as clickable
        for (HyperPosition hyperPos:availableHyperPositions) {
            x1=hyperPos.grid.x;
            y1=hyperPos.grid.y;
            x2=hyperPos.inGridPosition.x;
            y2=hyperPos.inGridPosition.y;
            btn[x1][y1][x2][y2].setTextColor(Color.BLACK);
            btn[x1][y1][x2][y2].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            btn[x1][y1][x2][y2].setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            btn[x1][y1][x2][y2].setText("?");
            btn[x1][y1][x2][y2].setClickable(true);
        }
    }

}