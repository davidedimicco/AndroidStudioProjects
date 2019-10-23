package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestPreparationFragment extends Fragment {

    private RadioButton direction1RadioButton;
    private RadioButton direction2RadioButton;
    //private RadioButton from1RadioButton;
    //private RadioButton from2RadioButton;
    //private RadioButton from3RadioButton;
    //private RadioButton to1RadioButton;
    //private RadioButton to2RadioButton;
    //private RadioButton to3RadioButton;
    private RadioButton fullTestRadioButton;
    private RadioButton partialTestRadioButton;
    private LinearLayout desiredCategoriesLinearLayout;
    private ArrayList<CheckBox> desiredCategoriesCheckBoxList;
    private Button homeBtn2;
    private Button startTestBtn;
    private MainActivity activity;


    public TestPreparationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_testpreparation, container, false);

        activity = (MainActivity)getActivity();

        direction1RadioButton=(RadioButton)rootView.findViewById(R.id.direction1RadioBtn);
        direction1RadioButton.setText("From "+activity.startingLanguage+" to "+activity.endingLanguage);

        direction2RadioButton=(RadioButton)rootView.findViewById(R.id.direction2RadioBtn);
        direction2RadioButton.setText("From "+activity.endingLanguage+" to "+activity.startingLanguage);

        /*
        from1RadioButton=(RadioButton)rootView.findViewById(R.id.fromWordRadioBtn);
        from1RadioButton.setText("From "+activity.startingLanguage+" (original)");

        from2RadioButton=(RadioButton)rootView.findViewById(R.id.fromTransliterationRadioBtn);
        from2RadioButton.setText("From "+activity.startingLanguage+" (transliteration)");

        from3RadioButton=(RadioButton)rootView.findViewById(R.id.fromTranslationRadioBtn);
        from3RadioButton.setText("From "+activity.endingLanguage);

        to1RadioButton=(RadioButton)rootView.findViewById(R.id.toWordRadioBtn);
        to1RadioButton.setText("To "+activity.startingLanguage+" (original)");

        to2RadioButton=(RadioButton)rootView.findViewById(R.id.toTransliterationRadioBtn);
        to2RadioButton.setText("To "+activity.startingLanguage+" (transliteration)");

        to3RadioButton=(RadioButton)rootView.findViewById(R.id.toTranslationRadioBtn);
        to3RadioButton.setText("To "+activity.endingLanguage);
        */

        fullTestRadioButton=(RadioButton)rootView.findViewById(R.id.fullTestRadioBtn);
        partialTestRadioButton=(RadioButton)rootView.findViewById(R.id.partialTestRadioBtn);
        desiredCategoriesLinearLayout=(LinearLayout) rootView.findViewById(R.id.desiredCategoriesLinearLayout);
        desiredCategoriesCheckBoxList=new ArrayList<CheckBox>();
        CheckBox temp;
        for(int i=0;i<activity.categories.size();i++){
            temp=new CheckBox(activity);
            temp.setText(activity.categories.get(i).getName());
            if(desiredCategoriesCheckBoxList.add(temp)){
                desiredCategoriesLinearLayout.addView(temp);
            }
        }
        homeBtn2=(Button)rootView.findViewById(R.id.homeBtn2);
        startTestBtn=(Button)rootView.findViewById(R.id.startTestBtn);

        direction1RadioButton.setChecked(true);
        direction1RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction1RadioButton.setChecked(true);
                direction2RadioButton.setChecked(false);
            }
        });

        direction2RadioButton.setChecked(false);
        direction2RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction1RadioButton.setChecked(false);
                direction2RadioButton.setChecked(true);
            }
        });

        /*
        from1RadioButton.setChecked(false);
        from1RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from1RadioButton.setChecked(true);
                from2RadioButton.setChecked(false);
                from3RadioButton.setChecked(false);
                to1RadioButton.setChecked(false);
            }
        });

        from2RadioButton.setChecked(true);
        from2RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from1RadioButton.setChecked(false);
                from2RadioButton.setChecked(true);
                from3RadioButton.setChecked(false);
                to2RadioButton.setChecked(false);
            }
        });

        from3RadioButton.setChecked(false);
        from3RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from1RadioButton.setChecked(false);
                from2RadioButton.setChecked(false);
                from3RadioButton.setChecked(true);
                to3RadioButton.setChecked(false);
            }
        });

        to1RadioButton.setChecked(false);
        to1RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to1RadioButton.setChecked(true);
                to2RadioButton.setChecked(false);
                to3RadioButton.setChecked(false);
                from1RadioButton.setChecked(false);
            }
        });

        to2RadioButton.setChecked(false);
        to2RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to1RadioButton.setChecked(false);
                to2RadioButton.setChecked(true);
                to3RadioButton.setChecked(false);
                from2RadioButton.setChecked(false);
            }
        });

        to3RadioButton.setChecked(true);
        to3RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to1RadioButton.setChecked(false);
                to2RadioButton.setChecked(false);
                to3RadioButton.setChecked(true);
                from3RadioButton.setChecked(false);
            }
        });
        */

        fullTestRadioButton.setChecked(true);
        fullTestRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullTestRadioButton.setChecked(true);
                partialTestRadioButton.setChecked(false);
            }
        });

        partialTestRadioButton.setChecked(false);
        partialTestRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullTestRadioButton.setChecked(false);
                partialTestRadioButton.setChecked(true);
            }
        });

        homeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the home fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        startTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.archive.size()==0){
                    Toast toast = Toast.makeText(activity, "You have to add some words in order to take the test!", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                activity.fromLanguage1toLanguage2=direction1RadioButton.isChecked();
                if(fullTestRadioButton.isChecked()){
                    activity.archiveForTest=activity.archive;
                    activity.mistakesForTest=activity.mistakes;
                }
                else{
                    activity.archiveForTest=new ArrayList<Entry>();
                    activity.mistakesForTest=new ArrayList<Entry>();
                    //metodo buzzurro ma che dovrebbe funzionare
                    for(int i=0;i<activity.archive.size();i++) {
                        for(int j=0;j<activity.categories.size();j++){
                            if(desiredCategoriesCheckBoxList.get(j).isChecked()){
                                if(activity.categories.get(j).getEntries().contains(activity.archive.get(i))){
                                    activity.archiveForTest.add(activity.archive.get(i));
                                    break;
                                }
                            }
                        }
                    }
                    for(int i=0;i<activity.mistakes.size();i++) {
                        for(int j=0;j<activity.categories.size();j++){
                            if(desiredCategoriesCheckBoxList.get(j).isChecked()){
                                if(activity.categories.get(j).getEntries().contains(activity.mistakes.get(i))){
                                    activity.mistakesForTest.add(activity.mistakes.get(i));
                                    break;
                                }
                            }
                        }
                    }
                }
                if(activity.archiveForTest.size()==0){
                    Toast toast = Toast.makeText(activity, "You have to select some words in order to take the test!", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                //Here I want to open the test fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new TestFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

}
