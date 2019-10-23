package it.unimi.dimicco.davide.myvocabulary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchiveFragment extends Fragment {


    private EditText wordText;
    private EditText translationsText;
    private Button newCategoryBtn;
    private EditText newCategoryEditText;
    //private ScrollView categoriesScrollView;
    private LinearLayout categoriesLinearLayout;
    private Button addBtn;
    private ArrayList<CheckBox> checkBoxList;

    private MainActivity activity;

    public ArchiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_archive, container, false);

        wordText=(EditText)rootView.findViewById(R.id.language1Text);
        translationsText=(EditText)rootView.findViewById(R.id.language2Text);
        newCategoryBtn=(Button)rootView.findViewById(R.id.newCategoryBtn);
        newCategoryEditText=(EditText)rootView.findViewById(R.id.newCategoryEditText);
        //categoriesScrollView=(ScrollView)rootView.findViewById(R.id.categoriesScrollView);
        categoriesLinearLayout=(LinearLayout)rootView.findViewById(R.id.categoriesLinearLayout);
        addBtn=(Button)rootView.findViewById(R.id.addBtn);
        activity=(MainActivity)getActivity();
        checkBoxList=new ArrayList<CheckBox>();
        CheckBox temp;
        for(int i=0;i<activity.categories.size();i++){
            temp=new CheckBox(activity);
            temp.setText(activity.categories.get(i).getName());
            if(checkBoxList.add(temp)){
                categoriesLinearLayout.addView(temp);
            }
        }

        newCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aggiungo la nuova categoria, aggiungo il nuovo checkbox al linear layout dentro la scrollview e infine cancello il nome scritto nella casella di testo
                activity.categories.add(new Category(newCategoryEditText.getText().toString()));
                CheckBox temp=new CheckBox(activity);
                //occhio agli indici se poi voglio riordinare in ordine alfabetico le categorie
                temp.setText(activity.categories.get(activity.categories.size()-1).getName());
                if(checkBoxList.add(temp)){
                    categoriesLinearLayout.addView(temp);
                }
                newCategoryEditText.setText("");
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Entry entry:Entry.parse(wordText.getText().toString(),translationsText.getText().toString())) {
                    if(!activity.archive.contains(entry)){
                        activity.archive.add(entry);
                        //aggiungo ad ogni categoria selezionata la parola aggiunta all'archivio, e nel frattempo deseleziono la categoria
                        for(int i=0;i<activity.categories.size();i++){
                            if(checkBoxList.get(i).isChecked()){
                                activity.categories.get(i).addIndex(activity.archive.size()-1);
                                checkBoxList.get(i).setChecked(false);
                            }
                        }
                    }
                }
                //Qua faccio quello che faceva prima il tasto save, cioè salvo categorie e archivio
                activity.serializeArchive();
                activity.serializeCategories();
            }
        });

        return rootView;
    }

    public void sortCategories(){

    }


}
