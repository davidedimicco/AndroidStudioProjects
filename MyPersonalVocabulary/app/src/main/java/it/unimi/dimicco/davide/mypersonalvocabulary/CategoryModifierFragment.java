package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Davide on 28/11/2017.
 */

public class CategoryModifierFragment extends Fragment {

    private MainActivity activity;
    private LinearLayout chosenCategoryLinearLayout;
    private LinearLayout chosenWordsLinearLayout;
    private ArrayList<TextView> categoriesTextViewList;
    private ArrayList<CheckBox> wordsCheckBoxList;
    private Button homeBtn4;
    private Button renameCategoryBtn;
    private Button deleteCategoryBtn;
    private View.OnClickListener categorySelectedListener;
    private View.OnClickListener addOrRemoveListener;
    private EditText searchEditText;
    int lastCategoryClicked;

    public CategoryModifierFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categorymodifier, container, false);

        activity=(MainActivity)getActivity();
        lastCategoryClicked =-1;

        searchEditText=(EditText)rootView.findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(lastCategoryClicked==-1){
                    return;
                }
                String hint=searchEditText.getText().toString();
                for(int i=0;i<activity.archive.size();i++){
                    if(activity.archive.get(i).toString().contains(hint)){
                        wordsCheckBoxList.get(i).setVisibility(View.VISIBLE);
                    }
                    else{
                        wordsCheckBoxList.get(i).setVisibility(View.GONE);
                    }
                }
            }
        });
        searchEditText.setEnabled(false);

        categorySelectedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastCategoryClicked >= 0) {
                    //Ricoloro l'ultimo cliccato
                    categoriesTextViewList.get(lastCategoryClicked).setBackgroundColor(Color.TRANSPARENT);
                }
                else{
                    searchEditText.setEnabled(true);
                    //Rendo visibile le checkbox, visto che prima non lo erano
                    for(int i=0;i<activity.archive.size();i++){
                        wordsCheckBoxList.get(i).setVisibility(View.VISIBLE);
                    }
                }
                //coloro quello appena cliccato
                v.setBackgroundColor(Color.GREEN);
                lastCategoryClicked =categoriesTextViewList.indexOf(v);
                Category cat=activity.categories.get(lastCategoryClicked);
                for(int i=0;i<activity.archive.size();i++){
                    if(cat.containsEntry(activity.archive.get(i))){
                        wordsCheckBoxList.get(i).setChecked(true);
                    }
                    else {
                        wordsCheckBoxList.get(i).setChecked(false);
                    }
                }
            }
        };

        addOrRemoveListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastCategoryClicked<0){
                    return;
                }
                //Prendo l'ultima categoria cliccata
                Category cat=activity.categories.get(lastCategoryClicked);
                int index=wordsCheckBoxList.indexOf(v);
                if(cat.containsEntry(activity.archive.get(index))){
                    cat.removeEntry(activity.archive.get(index));
                }
                else {
                    cat.addEntry(activity.archive.get(index));
                }
                //la prossima riga sarà poi da eliminare quando avrò messo tutti i salvataggi di "emergenza" nei metodi paused e simili
                activity.serializeCategories();
            }
        };

        Collections.sort(activity.categories);
        chosenCategoryLinearLayout=(LinearLayout)rootView.findViewById(R.id.chosenCategoryLinearLayout);
        categoriesTextViewList=new ArrayList<TextView>();
        TextView tempTV;
        for(int i=0;i<activity.categories.size();i++){
            tempTV=new TextView(activity);
            tempTV.setText(activity.categories.get(i).getName());
            tempTV.setClickable(true);
            tempTV.setBackgroundColor(Color.TRANSPARENT);
            tempTV.setOnClickListener(categorySelectedListener);
            categoriesTextViewList.add(tempTV);
            chosenCategoryLinearLayout.addView(tempTV);
        }

        Collections.sort(activity.archive);
        chosenWordsLinearLayout=(LinearLayout)rootView.findViewById(R.id.chosenWordsLinearLayout);
        wordsCheckBoxList=new ArrayList<CheckBox>();
        CheckBox tempCB;
        for(int i=0;i<activity.archive.size();i++){
            tempCB=new CheckBox(activity);
            tempCB.setText(activity.archive.get(i).toString());
            tempCB.setOnClickListener(addOrRemoveListener);
            tempCB.setVisibility(View.GONE);
            wordsCheckBoxList.add(tempCB);
            chosenWordsLinearLayout.addView(tempCB);
        }

        homeBtn4=(Button)rootView.findViewById(R.id.homeBtn4);
        homeBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here i save the changes of the categories
                activity.serializeCategories();
                //Here I want to go back to the home fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        renameCategoryBtn=(Button)rootView.findViewById(R.id.renameCategoryBtn);
        renameCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastCategoryClicked<0){
                    return;
                }


                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Enter the new name for the category '"+activity.categories.get(lastCategoryClicked)+"':");
                builder1.setCancelable(true);
                final EditText edittext = new EditText(activity);
                builder1.setView(edittext);

                builder1.setPositiveButton(
                        "Rename",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                activity.categories.get(lastCategoryClicked).setName(edittext.getText().toString());
                                categoriesTextViewList.get(lastCategoryClicked).setBackgroundColor(Color.TRANSPARENT);
                                lastCategoryClicked=-1;

                                categoriesTextViewList=new ArrayList<TextView>();
                                chosenCategoryLinearLayout.removeAllViews();
                                Collections.sort(activity.categories);
                                TextView tempTV;
                                for(int i=0;i<activity.categories.size();i++){
                                    tempTV=new TextView(activity);
                                    tempTV.setText(activity.categories.get(i).getName());
                                    tempTV.setClickable(true);
                                    tempTV.setBackgroundColor(Color.TRANSPARENT);
                                    tempTV.setOnClickListener(categorySelectedListener);
                                    categoriesTextViewList.add(tempTV);
                                    chosenCategoryLinearLayout.addView(tempTV);
                                }

                                for(int i=0;i<activity.archive.size();i++){
                                    wordsCheckBoxList.get(i).setVisibility(View.GONE);
                                }

                                //controllare se refresha la view oppure no.. in caso negativo devo farlo altrimenti riordinando le categorie faccio casino
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Back",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                //Here i save the changes of the categories
                activity.serializeCategories();
            }
        });

        deleteCategoryBtn=(Button)rootView.findViewById(R.id.deleteCategoryBtn);
        deleteCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastCategoryClicked<0){
                    return;
                }
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Do you really want to delete the category '"+activity.categories.get(lastCategoryClicked)+"' ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Cancello la categoria dall'arraylist di categorie contenuto in activity
                                activity.categories.remove(lastCategoryClicked);
                                //Rendo invisibile la categoria nel linear layout
                                categoriesTextViewList.get(lastCategoryClicked).setVisibility(View.GONE);
                                //Cancello la categoria dall'arraylist di textview
                                categoriesTextViewList.remove(lastCategoryClicked);
                                //Salvo quanto appena fatto
                                activity.serializeCategories();
                                lastCategoryClicked=-1;

                                for(int i=0;i<activity.archive.size();i++){
                                    wordsCheckBoxList.get(i).setVisibility(View.GONE);
                                }
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                //Here i save the changes of the categories
                activity.serializeCategories();
            }
        });

        return rootView;
    }



}