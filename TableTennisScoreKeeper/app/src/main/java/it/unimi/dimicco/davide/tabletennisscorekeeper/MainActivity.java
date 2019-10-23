package it.unimi.dimicco.davide.tabletennisscorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int score1;

    private int score2;

    private int nets;

    private int balls;

    private int max;

    private TextView scoreTextView1;

    private TextView scoreTextView2;

    TextView serverTextView1;

    TextView serverTextView2;

    Button netBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        balls=5;
        max=21;
        score1=0;
        score2=0;
        nets=0;
        netBtn=(Button)findViewById(R.id.netsBtn);
        scoreTextView1=(TextView)findViewById(R.id.scoreTextView1);
        scoreTextView2=(TextView)findViewById(R.id.scoreTextView2);
        serverTextView1=(TextView)findViewById(R.id.serverTextView1);
        serverTextView2=(TextView)findViewById(R.id.serverTextView2);
    }

    /*

    QUETA PARTE DI CODICE CREDO SERVA AD AGGIUNGERE I 3 PUNTINI DEI SETTING MA MANCA UN PEZZO DA QUALCHE PARTE

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    private void changeBall(){
        if((((score1+score2)/balls)%2)==1){
            serverTextView1.setText("");
            serverTextView2.setText(getString(R.string.your_turn));
        }
        else{
            serverTextView1.setText(getString(R.string.your_turn));
            serverTextView2.setText("");
        }
    }

    public void firstPlayerScores(View v){
        if((score1==max) || (score2==max)){
            return;
        }
        score1++;
        if(((score1+score2)%balls)==0){
            changeBall();
        }
        if((score1==max-1) && (score2==max-1)){
            vantaggi();
            return;
        }
        scoreTextView1.setText(score1+"");
        resetNets();
        if(score1==max){
            serverTextView1.setText(getString(R.string.you_won));
            serverTextView2.setText("");
            return;
        }
    }

    public void secondPlayerScores(View v){
        if((score1==max) || (score2==max)){
            return;
        }
        score2++;
        if(((score1+score2)%balls)==0){
            changeBall();
        }
        if((score1==max-1) && (score2==max-1)){
            vantaggi();
            return;
        }
        scoreTextView2.setText(score2+"");
        resetNets();
        if(score2==max){
            serverTextView1.setText("");
            serverTextView2.setText(getString(R.string.you_won));
            return;
        }
    }

    public void reset(View v){
        score1=0;
        score2=0;
        nets=0;
        balls=5;
        max=21;
        scoreTextView1.setText(score1+"");
        scoreTextView2.setText(score2+"");
        serverTextView1.setText(getString(R.string.your_turn));
        serverTextView2.setText("");
    }

    private void resetNets(){
        nets=0;
        netBtn.setText("Net "+(nets+1));
    }

    public void increaseNets(View v){
        if((score1==max) || (score2==max)){
            return;
        }
        nets++;
        if(nets==3){
            if((((score1+score2)/balls)%2)==0){
                firstPlayerScores(v);
            }
            else{
                secondPlayerScores(v);
            }
            nets=0;
        }
        netBtn.setText("Net "+(nets+1));
    }

    private void vantaggi(){
        max=2;
        balls=1;
        score1=0;
        score2=0;
        scoreTextView1.setText(score1+"");
        scoreTextView2.setText(score2+"");
    }

}
