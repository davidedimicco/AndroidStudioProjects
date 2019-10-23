package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Davide on 20/02/2018.
 */

public class WordModifierFragment extends Fragment {

    private MainActivity activity;
    private Entry entry;
    private TextView firstLanguageTextView;
    private EditText firstLanguageEditText;
    private TextView transliterationTextView;
    private EditText transliterationEditText;
    private TextView secondLanguageTextView;
    private EditText secondLanguageEditText;
    private LinearLayout wordsCategoriesLinearLayout;
    private ArrayList<CheckBox> wordsCategoriesCheckBoxList;
    private Button doneBtn;

    public WordModifierFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wordmodifier, container, false);

        activity=(MainActivity)getActivity();
        entry=activity.archive.get(this.getArguments().getInt("index",0));

        firstLanguageTextView=(TextView)rootView.findViewById(R.id.firstLanguageWordTextView);
        firstLanguageTextView.setText(activity.startingLanguage);

        transliterationTextView=(TextView)rootView.findViewById(R.id.transliterationWordTextView);
        transliterationTextView.setText(activity.startingLanguage);

        firstLanguageEditText=(EditText)rootView.findViewById(R.id.firstLanguageWordEditText);
        firstLanguageEditText.setText(entry.getWord());

        transliterationEditText=(EditText)rootView.findViewById(R.id.transliterationWordEditText);
        transliterationEditText.setText(entry.getTransliteration());

        secondLanguageTextView=(TextView)rootView.findViewById(R.id.secondLanguageWordTextView);
        secondLanguageTextView.setText(activity.endingLanguage);

        secondLanguageEditText=(EditText)rootView.findViewById(R.id.secondLanguageWordEditText);
        secondLanguageEditText.setText(entry.getTranslation());

        wordsCategoriesLinearLayout=(LinearLayout)rootView.findViewById(R.id.wordsCategoriesLinearLayout);
        wordsCategoriesCheckBoxList=new ArrayList<CheckBox>();
        Collections.sort(activity.categories);
        CheckBox temp;
        for(int i=0;i<activity.categories.size();i++){
            temp=new CheckBox(activity);
            temp.setText(activity.categories.get(i).getName());
            if(activity.categories.get(i).containsEntry(entry)){
                temp.setChecked(true);
            }
            else{
                temp.setChecked(false);
            }
            wordsCategoriesCheckBoxList.add(temp);
            wordsCategoriesLinearLayout.addView(temp);
        }


        doneBtn=(Button)rootView.findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entry.setWord(firstLanguageEditText.getText().toString());
                entry.setTransliteration(transliterationEditText.getText().toString());
                entry.setTranslation(secondLanguageEditText.getText().toString());
                for(int i=0;i<activity.categories.size();i++){
                    if(wordsCategoriesCheckBoxList.get(i).isChecked()){
                        activity.categories.get(i).addEntry(entry);
                    }
                    else{
                        activity.categories.get(i).removeEntry(entry);
                    }
                }
                //Here i save the changes of the categories
                activity.serializeCategories();
                //Here i save the changes of the archive
                activity.serializeArchive();
                //Here i save the changes of the mistakes
                activity.serializeMistakes();
                //Here I want to go back to the home fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new WordSettingsFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }



}