package it.unimi.dimicco.davide.myfitnessapp;


import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChronometerFragment extends Fragment {

    private Chronometer chronometer;

    private TextView lapsTextView;

    private ScrollView lapsScrollView;

    private Button startBtn;

    private Button lapBtn;

    private Button stopBtn;

    private int lapsCounter;

    private boolean isRunning;

    private int h,m,s;

    private String lapsText;

    private long startingTime;

    public ChronometerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_chronometer, container, false);

        chronometer=(Chronometer)rootView.findViewById(R.id.chronometer);
        lapsTextView=(TextView)rootView.findViewById(R.id.lapsTextView);
        lapsScrollView=(ScrollView)rootView.findViewById(R.id.lapsScrollView);
        startBtn=(Button)rootView.findViewById(R.id.startBtn);
        lapBtn=(Button)rootView.findViewById(R.id.lapBtn);
        stopBtn=(Button)rootView.findViewById(R.id.stopBtn);


        chronometer.setText(String.format("%02d:%02d:%02d",h,m,s));
        lapsTextView.setText(lapsText);
        if(isRunning){
            chronometer.setBase(startingTime);
            chronometer.start();
        }
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cm) {
                long time = SystemClock.elapsedRealtime() - cm.getBase();
                h   = (int)(time /3600000);
                m = (int)(time - h*3600000)/60000;
                s= (int)(time - h*3600000- m*60000)/1000 ;
                cm.setText(String.format("%02d:%02d:%02d",h,m,s));
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRunning) {
                    lapsCounter = 0;
                    lapsText="";
                    lapsTextView.setText(lapsText);
                    isRunning=true;

                    startingTime=SystemClock.elapsedRealtime();
                    chronometer.setBase(startingTime);
                    //chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                }
            }
        });

        lapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunning) {
                    lapsCounter++;
                    lapsText +="\n"+" Lap "+lapsCounter+": "+chronometer.getText().toString();
                    lapsTextView.setText(lapsText);
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
                if(isRunning){
                    chronometer.stop();
                    isRunning=false;
                }
            }
        });


        return rootView;
    }



}
