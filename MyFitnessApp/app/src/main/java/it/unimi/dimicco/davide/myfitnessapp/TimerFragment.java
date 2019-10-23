package it.unimi.dimicco.davide.myfitnessapp;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {


    private Button startTimerBtn;
    private Button resetTimerBtn;
    private EditText hoursEditText;
    private TextView aTextView;
    private EditText minutesEditText;
    private TextView bTextView;
    private EditText secondsEditText;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    //private long timeInMillis;
    private int h,m,s;


    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_timer, container, false);

        startTimerBtn = (Button) rootView.findViewById(R.id.startTimerBtn);
        resetTimerBtn = (Button) rootView.findViewById(R.id.resetTimerBtn);
        timerTextView = (TextView) rootView.findViewById(R.id.timerTextView);
        hoursEditText = (EditText) rootView.findViewById(R.id.hoursEditText);
        aTextView = (TextView) rootView.findViewById(R.id.aTextView);
        minutesEditText = (EditText) rootView.findViewById(R.id.minutesEditText);
        bTextView = (TextView) rootView.findViewById(R.id.bTextView);
        secondsEditText = (EditText) rootView.findViewById(R.id.secondsEditText);

        timerTextView.setText(String.format("%02d:%02d:%02d", h,m,s));

        startTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTimerRunning) {
                    startTimer();
                    isTimerRunning=true;
                }
            }
        });

        resetTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTimerRunning) {
                    isTimerRunning=false;
                    countDownTimer.cancel();
                }
            }
        });

        return rootView;
    }


    private void startTimer() {
        int hours=Integer.parseInt(hoursEditText.getText().toString());
        int minutes=Integer.parseInt(minutesEditText.getText().toString());
        int seconds=Integer.parseInt(secondsEditText.getText().toString());
        if(seconds>59){
            minutes+=seconds/60;
            seconds=seconds%60;
        }
        if(minutes>59){
            hours+=minutes/60;
            minutes=minutes%60;
        }
        long timeInMillis = 1000*((3600*hours)+(60*minutes)+seconds);
        hoursEditText.setText(String.format("%02d",hours));
        minutesEditText.setText(String.format("%02d",minutes));
        secondsEditText.setText(String.format("%02d",seconds));
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                s=(int)((leftTimeInMilliseconds/1000)%60);
                m=(int)((leftTimeInMilliseconds/60000)%60);
                h=(int)((leftTimeInMilliseconds/3600000)%24);
                //qua devo aggiornare quello che stampo, altrimenti stampa sempre il tempo iniziale
                timerTextView.setText(String.format("%02d:%02d:%02d", h,m,s));
            }

            @Override
            public void onFinish() {
                isTimerRunning=false;
                countDownTimer.cancel();
                timerTextView.setText("Time up!");
                //qua voglio mettere che segna 00:00:00, ma parte un suono che mi dice che è finito il tempo
            }

        };
        countDownTimer.start();

    }

}
