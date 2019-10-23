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
 * Created by Davide on 19/11/2017.
 */

public class AddWordsFragment  extends Fragment {


    private EditText wordText;
    private EditText wordTransliterationText;
    private EditText translationsText;
    private Button newCategoryBtn;
    private EditText newCategoryEditText;
    private LinearLayout categoriesLinearLayout;
    private ArrayList<CheckBox> categoriesCheckBoxList;
    private Button homeBtn1;
    private Button addBtn;
    private TextView fromToTextView;

    private MainActivity activity;

    public AddWordsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_addwords, container, false);

        activity=(MainActivity)getActivity();
        fromToTextView=(TextView)rootView.findViewById((R.id.fromToTextView));
        fromToTextView.setText(activity.startingLanguage+" - "+activity.endingLanguage);
        wordText=(EditText)rootView.findViewById(R.id.language1Text);
        wordTransliterationText=(EditText)rootView.findViewById(R.id.transliterationText);
        translationsText=(EditText)rootView.findViewById(R.id.language2Text);
        newCategoryBtn=(Button)rootView.findViewById(R.id.newCategoryBtn);
        newCategoryEditText=(EditText)rootView.findViewById(R.id.newCategoryEditText);
        //categoriesScrollView=(ScrollView)rootView.findViewById(R.id.categoriesScrollView);
        categoriesLinearLayout=(LinearLayout)rootView.findViewById(R.id.categoriesLinearLayout);
        addBtn=(Button)rootView.findViewById(R.id.addBtn);
        homeBtn1=(Button)rootView.findViewById(R.id.homeBtn1);
        categoriesCheckBoxList =new ArrayList<CheckBox>();
        Collections.sort(activity.categories);
        CheckBox temp;
        for(int i=0;i<activity.categories.size();i++){
            temp=new CheckBox(activity);
            temp.setText(activity.categories.get(i).getName());
            /*PERCHÈ LO AVEVO SCRITTO COSI?
            if(categoriesCheckBoxList.add(temp)){
                categoriesLinearLayout.addView(temp);
            }
            */
            categoriesCheckBoxList.add(temp);
            categoriesLinearLayout.addView(temp);
        }

        newCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aggiungo la nuova categoria, aggiungo il nuovo checkbox al linear layout dentro la scrollview e infine cancello il nome scritto nella casella di testo
                Category category=new Category(newCategoryEditText.getText().toString());
                //Non voglio che ci siano ripetizioni nell'arraylist delle categorie
                if(activity.categories.contains(category)){
                    return;
                }
                activity.categories.add(category);
                /*
                CheckBox temp=new CheckBox(activity);
                temp.setText(activity.categories.get(activity.categories.size()-1).getName());
                if(categoriesCheckBoxList.add(temp)){
                    categoriesLinearLayout.addView(temp);
                }
                newCategoryEditText.setText("");
                */
                categoriesCheckBoxList =new ArrayList<CheckBox>();
                categoriesLinearLayout.removeAllViews();
                Collections.sort(activity.categories);
                CheckBox temp;
                for(int i=0;i<activity.categories.size();i++){
                    temp=new CheckBox(activity);
                    temp.setText(activity.categories.get(i).getName());
                    categoriesCheckBoxList.add(temp);
                    categoriesLinearLayout.addView(temp);
                }
                newCategoryEditText.setText("");
                activity.serializeCategories();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Entry entry:Entry.parse(wordText.getText().toString(),wordTransliterationText.getText().toString(),translationsText.getText().toString())) {
                    if(!activity.archive.contains(entry)){
                        activity.archive.add(entry);
                        //pulisco gli editText
                        wordText.setText("");
                        wordTransliterationText.setText("");
                        translationsText.setText("");
                        //aggiungo ad ogni categoria selezionata la parola aggiunta all'archivio, e nel frattempo deseleziono la categoria
                        for(int i=0;i<activity.categories.size();i++){
                            if(categoriesCheckBoxList.get(i).isChecked()){
                                activity.categories.get(i).addEntry(entry);
                                categoriesCheckBoxList.get(i).setChecked(false);
                            }
                        }
                    }
                }
                //Qua faccio quello che faceva prima il tasto save, cioè salvo categorie e archivio
                activity.serializeArchive();
                activity.serializeCategories();
            }
        });

        homeBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to go back to the home fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

    /*
    @Override
    public void OnPause(){

    }

    @Override
    public void OnStop(){

    }
    */

}
