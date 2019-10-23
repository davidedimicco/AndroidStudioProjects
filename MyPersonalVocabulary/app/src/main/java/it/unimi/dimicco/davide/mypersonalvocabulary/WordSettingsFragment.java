package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Davide on 20/02/2018.
 */

public class WordSettingsFragment extends Fragment {

    private MainActivity activity;
    private EditText searchWordEditText;
    private LinearLayout wordsLinearLayout;
    private Button homeBtn5;
    private Button deleteBtn;
    private Button modifyBtn;

    private ArrayList<TextView> wordsTextViewList;
    private View.OnClickListener wordSelectedListener;
    int lastWordClicked;

    public WordSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wordsettings, container, false);

        activity=(MainActivity)getActivity();
        lastWordClicked =-1;

        searchWordEditText=(EditText)rootView.findViewById(R.id.searchWordEditText);
        searchWordEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String hint=searchWordEditText.getText().toString();
                for(int i=0;i<activity.archive.size();i++){
                    if(activity.archive.get(i).toString().contains(hint)){
                        wordsTextViewList.get(i).setVisibility(View.VISIBLE);
                    }
                    else{
                        wordsTextViewList.get(i).setVisibility(View.GONE);
                    }
                }
                if(lastWordClicked>=0 && !wordsTextViewList.get(lastWordClicked).toString().contains(hint)){
                    wordsTextViewList.get(lastWordClicked).setBackgroundColor(Color.TRANSPARENT);
                    lastWordClicked=-1;
                }
            }
        });

        wordSelectedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastWordClicked >= 0) {
                    //Ricoloro l'ultimo cliccato
                    wordsTextViewList.get(lastWordClicked).setBackgroundColor(Color.TRANSPARENT);
                }
                //coloro quello appena cliccato
                v.setBackgroundColor(Color.GREEN);
                lastWordClicked=wordsTextViewList.indexOf(v);
                deleteBtn.setClickable(true);
                modifyBtn.setClickable(true);
            }
        };

        Collections.sort(activity.archive);
        wordsLinearLayout=(LinearLayout)rootView.findViewById(R.id.wordsLinearLayout);
        wordsTextViewList=new ArrayList<TextView>();
        TextView tempTV;
        for(int i=0;i<activity.archive.size();i++){
            tempTV=new TextView(activity);
            tempTV.setText(activity.archive.get(i).toString());
            tempTV.setClickable(true);
            tempTV.setBackgroundColor(Color.TRANSPARENT);
            tempTV.setOnClickListener(wordSelectedListener);
            wordsTextViewList.add(tempTV);
            wordsLinearLayout.addView(tempTV);
        }


        homeBtn5=(Button)rootView.findViewById(R.id.homeBtn5);
        homeBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here i save the changes of the categories
                activity.serializeCategories();
                //Here i save the changes of the archive
                activity.serializeArchive();
                //Here i save the changes of the mistakes
                activity.serializeMistakes();
                //Here I want to go back to the home fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        deleteBtn=(Button)rootView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I have to delete the entry from all the files and from the views in this page
                wordsTextViewList.remove(lastWordClicked);
                wordsLinearLayout.removeViewAt(lastWordClicked);
                Entry entry=activity.archive.get(lastWordClicked);
                activity.archive.remove(entry);
                while(activity.mistakes.remove(entry)){}
                for(int i=0;i<activity.categories.size();i++){
                    if(activity.categories.get(i).containsEntry(entry)){
                        activity.categories.get(i).removeEntry(entry);
                    }
                }
                //Here i save the changes of the categories
                activity.serializeCategories();
                //Here i save the changes of the archive
                activity.serializeArchive();
                //Here i save the changes of the mistakes
                activity.serializeMistakes();
                lastWordClicked=-1;
                deleteBtn.setClickable(false);
                modifyBtn.setClickable(false);
            }
        });
        deleteBtn.setClickable(false);

        //NON FUNZIONA!!!
        modifyBtn=(Button)rootView.findViewById(R.id.modifyBtn);
        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastWordClicked>=0){
                    Bundle bundle = new Bundle();
                    bundle.putInt("index",lastWordClicked);
                    WordModifierFragment frag = new WordModifierFragment();
                    frag.setArguments(bundle);
                    //Here I want to go to the entry modifier fragment
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, frag);
                    //fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
        modifyBtn.setClickable(false);

        return rootView;
    }



}