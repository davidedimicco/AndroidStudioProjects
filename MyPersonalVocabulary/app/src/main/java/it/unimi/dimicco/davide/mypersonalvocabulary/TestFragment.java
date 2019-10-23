package it.unimi.dimicco.davide.mypersonalvocabulary;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


    private TextView rightWrongTextView;
    private TextView questionTextView;
    private Button showHintBtn;
    private TextView hintTextView;
    private EditText answerEditText;
    private Button continueBtn;
    private Button endTestBtn;

    private int index;
    private Entry question;
    private Random random;
    private MainActivity activity;
    private int rightCounter, wrongCounter;
    private String partialStats;


    public TestFragment() {
        // Required empty public constructor
    }

    //WHEN REDOING IT, CHECK IF THE TRIM()'S ARE NECESSARY!!!

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_test, container, false);

        activity = (MainActivity)getActivity();
        rightWrongTextView=(TextView)rootView.findViewById(R.id.rightWrongTextView);
        questionTextView = (TextView) rootView.findViewById(R.id.questionTextView);
        showHintBtn = (Button) rootView.findViewById(R.id.showHintBtn);
        hintTextView = (TextView) rootView.findViewById(R.id.hintTextView);
        answerEditText = (EditText) rootView.findViewById(R.id.answerEditText);
        continueBtn = (Button) rootView.findViewById(R.id.continueBtn);
        endTestBtn = (Button) rootView.findViewById(R.id.endTestBtn);

        index=0;
        wrongCounter=0;
        rightCounter=0;
        random = new Random();

        activity.statistics="";
        partialStats="";
        next();

        showHintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintTextView.setVisibility(View.VISIBLE);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QUA DEVO SCRIVERE COSA DEVE FARE IL PROGRAMMA UNA VOLTA CHE GLI CHIEDO DI CONTROLLARE UNA PAROLA
                ArrayList<String> answers=new ArrayList<String>();
                ArrayList<String> wrong=new ArrayList<String>();
                boolean isCorrect=true;
                String s=answerEditText.getText().toString();
                while(s.contains(",")){
                    answers.add(s.substring(0,s.indexOf(",")).trim());
                    s=s.substring(s.indexOf(",")+1);
                }
                answers.add(s.trim());


                if(activity.fromLanguage1toLanguage2){
                    if(question.getWord().equals("")){
                        for(int i=0;i<answers.size();i++){
                            if(!(Entry.getTranslationsFromTransliteration(activity.archiveForTest,question.getTransliteration())).contains(answers.get(i))){
                                isCorrect=false;
                                wrong.add(answers.get(i));
                            }
                        }
                    }
                    else{
                        for(int i=0;i<answers.size();i++){
                            if(!(Entry.getTranslationsFromWord(activity.archiveForTest,question.getWord())).contains(answers.get(i))){
                                isCorrect=false;
                                wrong.add(answers.get(i));
                            }
                        }
                    }
                }
                else{
                    for(int i=0;i<answers.size();i++){
                        if((answers.get(i).equals(""))||(!((Entry.getWordsFromTranslation(activity.archiveForTest,question.getTranslation())).contains(answers.get(i))||((Entry.getTransliterationsFromTranslation(activity.archiveForTest,question.getTranslation())).contains(answers.get(i)))))){
                            isCorrect=false;
                            wrong.add(answers.get(i));
                        }
                    }
                }
                reactionToAnswer(isCorrect, answers, wrong);
                next();
            }
        });

        endTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.statistics="Right = "+rightCounter+"\n"+"Wrong = "+wrongCounter+partialStats;
                activity.serializeMistakes();
                //Here I want to open the statistics fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new StatisticsFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

    //DA AGGIUNGERE LA RISPOSTA CORRETTA NEL TOAST IN CASO DI ERRORE!
    public void reactionToAnswer(boolean correct,ArrayList<String> answers, ArrayList<String> wrongs){
        Toast toast;
        if(correct){
            toast = Toast.makeText(activity, "RIGHT!", Toast.LENGTH_SHORT);
            View view = toast.getView();
            TextView text = (TextView) view.findViewById(android.R.id.message);
            text.setBackgroundColor(Color.GREEN);
            rightCounter++;
        }
        else{
            toast = Toast.makeText(activity, "WRONG!", Toast.LENGTH_SHORT);
            View view = toast.getView();
            TextView text = (TextView) view.findViewById(android.R.id.message);
            text.setBackgroundColor(Color.RED);
            wrongCounter++;
        }
        toast.show();
        updateStatisticsAndMistakes(correct, answers, wrongs);
    }




    //DA RIFARE DA CAPO: PER ORA NEL CASO DI RISPOSTA CORRETTA ELIMINA DAGLI ERRORI LA ENTRY CORRISPONDENTE ALLA DOMANDA (CHE NON è DETTO COMBACI CON LA RISPOSTA)
    public void updateStatisticsAndMistakes(boolean correct, ArrayList<String> answers, ArrayList<String> wrongs){
        if(correct){
            if(activity.fromLanguage1toLanguage2){
                for(int i=0;i<answers.size();i++){
                    activity.mistakesForTest.remove(new Entry(question.getWord(),question.getTransliteration(),answers.get(i)));
                    if(activity.mistakesForTest!=activity.mistakes){
                        activity.mistakes.remove(new Entry(question.getWord(),question.getTransliteration(),answers.get(i)));
                    }
                }
            }
            else{
                for(int i=0;i<answers.size();i++){
                    activity.mistakesForTest.remove(new Entry("",answers.get(i),question.getTranslation()));
                    if(activity.mistakesForTest!=activity.mistakes){
                        activity.mistakes.remove(new Entry("",answers.get(i),question.getTranslation()));
                    }
                }
            }
        }
        else{
            activity.mistakesForTest.add(question);
            if(activity.mistakesForTest!=activity.mistakes){
                activity.mistakes.add(question);
            }
            if(activity.fromLanguage1toLanguage2){
                partialStats=partialStats+"\n"+question.getTransliteration()+"="+question.getTranslation()+" (you wrote \""+answers.toString()+"\")";
            }
            else{
                partialStats=partialStats+"\n"+question.getTranslation()+"="+question.getTransliteration()+" (you wrote \""+answers.toString()+"\")";
            }
        }

    }

    public void next(){
        rightWrongTextView.setText("Right: "+rightCounter+"     Wrong: "+wrongCounter+"");
        index=random.nextInt(activity.archiveForTest.size()+activity.mistakesForTest.size());

        if(index<activity.archiveForTest.size()){
            question=activity.archiveForTest.get(index);
        }
        else{
            question=activity.mistakesForTest.get(index-activity.archiveForTest.size());
        }

        if(activity.fromLanguage1toLanguage2){
            if(question.getWord().equals("")){
                questionTextView.setText(question.getTransliteration());
                showHintBtn.setVisibility(View.GONE);
                hintTextView.setVisibility(View.GONE);
            }
            else{
                questionTextView.setText(question.getWord());
                showHintBtn.setVisibility(View.VISIBLE);
                hintTextView.setText(question.getTransliteration());
                hintTextView.setVisibility(View.INVISIBLE);
            }
        }
        else{
            questionTextView.setText(question.getTranslation());
            showHintBtn.setVisibility(View.GONE);
            hintTextView.setVisibility(View.GONE);
        }

        answerEditText.setText("");
    }

}
