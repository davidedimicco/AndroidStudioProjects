package it.unimi.dimicco.davide.myvocabulary;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


    private TextView language1TextView;
    private Button directionBtn;
    private TextView language2TextView;
    private Button startBtn;
    private Button stopBtn;
    private TextView questionTextView;
    private EditText answerEditText;
    private Button checkBtn;
    private boolean fromLeftToRight;
    private int index;
    private Random random;
    private MainActivity activity;
    private int rightCounter, wrongCounter;
    private String partialStats;


    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_test, container, false);

        language1TextView = (TextView) rootView.findViewById(R.id.language1Text);
        directionBtn = (Button)rootView.findViewById(R.id.directionBtn);
        language2TextView = (TextView) rootView.findViewById(R.id.language2Text);
        startBtn = (Button) rootView.findViewById(R.id.startBtn);
        stopBtn = (Button) rootView.findViewById(R.id.stopBtn);
        questionTextView = (TextView) rootView.findViewById(R.id.questionTextView);
        answerEditText = (EditText) rootView.findViewById(R.id.answerEditText);
        checkBtn = (Button) rootView.findViewById(R.id.checkBtn);
        fromLeftToRight=true;
        index=0;
        wrongCounter=0;
        rightCounter=0;
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        checkBtn.setEnabled(false);
        directionBtn.setEnabled(true);
        random = new Random();
        activity = (MainActivity)getActivity();
        activity.statistics="";
        partialStats="";

        directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromLeftToRight=!fromLeftToRight;
                directionBtn.setText(fromLeftToRight? "==>" : "<==");
                questionTextView.setText("");
                answerEditText.setText("");
            }
        });


        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.archive.equals(null))
                    return;
                startBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                partialStats="";
                wrongCounter=0;
                rightCounter=0;
                checkBtn.setEnabled(true);
                directionBtn.setEnabled(false);
                next();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QUA DEVO FERMARE IL TEST E FAR COMPARIRE LE STATISTICHE NELLA FINESTRA STATISTICS
                startBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                checkBtn.setEnabled(false);
                directionBtn.setEnabled(true);
                activity.statistics="Right = "+rightCounter+"\n"+"Wrong = "+wrongCounter+partialStats;
                //((StatisticsFragment)activity.adapter.getItem(2)).statisticsTextView.setText(activity.statistics);
                //devo trovare un modo alternativo per aggiornare lo statisticsfragment
                //StatisticsFragment fragment=(StatisticsFragment)rootView.findViewById(activity.adapter.getItemId(2))
                //(StatisticsFragment)activity.getSupportFragmentManager().findFragmentById(activity.adapter.getItemId(2));
                activity.serializeMistakes();
                checkBtn.setBackgroundColor(Color.LTGRAY);
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QUA DEVO SCRIVERE COSA DEVE FARE IL PROGRAMMA UNA VOLTA CHE GLI CHIEDO DI CONTROLLARE UNA PAROLA
                if(fromLeftToRight){
                    if(index<activity.archive.size()){
                        if((Entry.getImages(activity.archive,activity.archive.get(index).getFirstWord())).contains(answerEditText.getText().toString())){
                            checkBtn.setBackgroundColor(Color.GREEN);
                            rightCounter++;
                            //questo lavoro è un pò una sporcizia, nel senso che se ho A=B e A=C negli errori, se lui mi chiede di dirgli B in A=B, e io gli dico C, invece di togliere A=C dagli errori non fa nulla
                            if(activity.archive.get(index).getSecondWord().equals(answerEditText.getText().toString())){
                                activity.mistakes.remove(activity.archive.get(index));
                            }
                        }
                        else{
                            checkBtn.setBackgroundColor(Color.RED);
                            wrongCounter++;
                            partialStats=partialStats+activity.archive.get(index).toString()+" (you wrote \""+answerEditText.getText().toString()+"\")";
                            activity.mistakes.add(activity.archive.get(index));
                        }
                    }
                    else{
                        if((Entry.getImages(activity.archive,activity.mistakes.get(index-activity.archive.size()).getFirstWord())).contains(answerEditText.getText().toString())){
                            checkBtn.setBackgroundColor(Color.GREEN);
                            rightCounter++;
                            //questo lavoro è un pò una sporcizia, nel senso che se ho A=B e A=C negli errori, se lui mi chiede di dirgli B in A=B, e io gli dico C, invece di togliere A=C dagli errori non fa nulla
                            if(activity.mistakes.get(index-activity.archive.size()).getSecondWord().equals(answerEditText.getText().toString())){
                                activity.mistakes.remove(activity.mistakes.get(index-activity.archive.size()));
                            }
                        }
                        else{
                            checkBtn.setBackgroundColor(Color.RED);
                            wrongCounter++;
                            partialStats=partialStats+activity.mistakes.get(index-activity.archive.size()).toString()+" (you wrote \""+answerEditText.getText().toString()+"\")";
                            activity.mistakes.add(activity.mistakes.get(index-activity.archive.size()));
                        }
                    }
                }
                else{
                    if(index<activity.archive.size()){
                        if((Entry.getPreImages(activity.archive,activity.archive.get(index).getSecondWord())).contains(answerEditText.getText().toString())){
                            checkBtn.setBackgroundColor(Color.GREEN);
                            rightCounter++;
                            //questo lavoro è un pò una sporcizia, nel senso che se ho A=B e C=B negli errori, se lui mi chiede di dirgli A in A=B, e io gli dico C, invece di togliere C=B dagli errori non fa nulla
                            if(activity.archive.get(index).getFirstWord().equals(answerEditText.getText().toString())){
                                activity.mistakes.remove(activity.archive.get(index));
                            }
                        }
                        else{
                            checkBtn.setBackgroundColor(Color.RED);
                            wrongCounter++;
                            partialStats=partialStats+activity.archive.get(index).toString()+" (you wrote \""+answerEditText.getText().toString()+"\")";
                            activity.mistakes.add(activity.archive.get(index));
                        }
                    }
                    else{
                        if((Entry.getPreImages(activity.archive,activity.mistakes.get(index-activity.archive.size()).getSecondWord())).contains(answerEditText.getText().toString())){
                            checkBtn.setBackgroundColor(Color.GREEN);
                            rightCounter++;
                            //questo lavoro è un pò una sporcizia, nel senso che se ho A=B e C=B negli errori, se lui mi chiede di dirgli A in A=B, e io gli dico C, invece di togliere C=B dagli errori non fa nulla
                            if(activity.mistakes.get(index-activity.archive.size()).getFirstWord().equals(answerEditText.getText().toString())){
                                activity.mistakes.remove(activity.mistakes.get(index-activity.archive.size()));
                            }
                        }
                        else{
                            checkBtn.setBackgroundColor(Color.RED);
                            wrongCounter++;
                            partialStats=partialStats+activity.mistakes.get(index-activity.archive.size()).toString()+" (you wrote \""+answerEditText.getText().toString()+"\")";
                            activity.mistakes.add(activity.mistakes.get(index-activity.archive.size()));
                        }
                    }
                }
                next();
            }
        });
        return rootView;
    }


    public void next(){
        index=random.nextInt(activity.archive.size()+activity.mistakes.size());
        if(index<activity.archive.size()){
            if(fromLeftToRight){
                questionTextView.setText(activity.archive.get(index).getFirstWord());
            }
            else{
                questionTextView.setText(activity.archive.get(index).getSecondWord());
            }
        }
        else{
            if(fromLeftToRight){
                questionTextView.setText(activity.mistakes.get(index-activity.archive.size()).getFirstWord());
            }
            else{
                questionTextView.setText(activity.mistakes.get(index-activity.archive.size()).getSecondWord());
            }
        }
        answerEditText.setText("");
    }

}
