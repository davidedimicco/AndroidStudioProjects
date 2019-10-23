package it.unimi.dimicco.davide.myfirstchronometer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timeTextView;

    private TextView lapsTextView;

    private Button startBtn;

    private Button lapBtn;

    private Button stopBtn;

    private ScrollView lapsScrollView;

    private Chronometer chronometer;

    private Thread thread;

    private Context context;

    private int lapsCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        timeTextView=(TextView)findViewById(R.id.timeTextView);
        lapsTextView=(TextView)findViewById(R.id.lapsTextView);
        startBtn=(Button)findViewById(R.id.startBtn);
        lapBtn=(Button)findViewById(R.id.lapBtn);
        stopBtn=(Button)findViewById(R.id.stopBtn);
        lapsScrollView=(ScrollView)findViewById(R.id.lapsScrollView);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer==null){
                    chronometer=new Chronometer(context);
                    thread=new Thread(chronometer);
                    thread.start();
                    chronometer.start();
                    lapsCounter=0;
                    lapsTextView.setText("");
                }
            }
        });

        lapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer!=null){
                    lapsCounter++;
                    String s=lapsTextView.getText().toString();
                    s=s+"\n"+" Lap "+lapsCounter+": "+timeTextView.getText().toString();
                    lapsTextView.setText(s);
                    lapsScrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lapsScrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    }, 100);
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chronometer!=null){
                    chronometer.stop();
                    thread.interrupt();
                    thread=null;
                    chronometer=null;
                }
            }
        });
    }

    public void updateTimerText(final String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run(){
                timeTextView.setText(time);
            }
        });
    }

}
