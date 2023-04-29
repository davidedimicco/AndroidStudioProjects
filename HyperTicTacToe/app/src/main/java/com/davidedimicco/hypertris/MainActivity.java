package com.davidedimicco.hypertris;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    HyperTrisModel model;
    Button resetBtn;
    Button[][][][] btn;
    GridLayout[][] bigGrid;
    String[][] tris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model=new HyperTrisModel();

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
                                btn[i1][i2][i3][i4].setClickable(true);
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
        //I create the 4D-array of buttons and associate the same click listener to all the buttons
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

                //Understand last action from clicked btn
                ArrayList<Integer> indices = getButtonIndices(btn);
                HyperPosition action = new HyperPosition(new Position(indices.get(0),indices.get(1)), new Position(indices.get(2),indices.get(3)));

                //Get playable grid for next move, from action
                model.nextGridPosition=action.inGridPosition;

                //Here I update the board
                model.partial_winner = model.board.update(action);
                if(model.partial_winner!=0){
                    bigGrid[action.grid.x][action.grid.y].setBackgroundColor(model.board.oddTurn? Color.BLUE : Color.RED);
                }

                //Get playable hyperpositions for next move, from playable grid and board
                ArrayList<HyperPosition> availableHyperPositions=model.board.availableHyperPositions(model.nextGridPosition);

                //Delete all the question marks that showed the available hyperpositions of the previous turn and set their text as empty.
                //At the same time set all buttons to be unclickable
                deleteQuestionMarks();

                //Here I update the view
                //First I write the new symbol
                btn.setTextColor(model.board.oddTurn? Color.BLUE : Color.RED);
                btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f);
                btn.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                btn.setText(model.board.oddTurn? "X" : "O");
                //Then, if the game is not over, I show the available hyperpositions for the next move
                if(model.board.winner==null){
                    writeQuestionMarks(availableHyperPositions);
                }
            }
        };

        for(int i1=0;i1<3;i1++){
            for(int i2=0;i2<3;i2++){
                for(int i3=0;i3<3;i3++){
                    for(int i4=0;i4<3;i4++){
                        btn[i1][i2][i3][i4].setOnClickListener(listener);
                        btn[i1][i2][i3][i4].setClickable(true);
                    }
                }
            }
        }
        //I create the 3x3 matrix of grids
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

//  DAED: added this, not sure it's necessary
        tris = new String[3][3];
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
        //In theory I should never pass by here.. to be checked
        return null;
    }

    public void deleteQuestionMarks(){
        for(int i1=0;i1<3;i1++){
            for(int i2=0;i2<3;i2++){
                for(int i3=0;i3<3;i3++){
                    for(int i4=0;i4<3;i4++){
                        btn[i1][i2][i3][i4].setClickable(false);
                        if(btn[i1][i2][i3][i4].getText().toString().equals("?")){
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