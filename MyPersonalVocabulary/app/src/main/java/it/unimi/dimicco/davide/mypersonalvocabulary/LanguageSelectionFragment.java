package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Davide on 19/11/2017.
 */

public class LanguageSelectionFragment extends Fragment {

    private MainActivity activity;
    private EditText language1EditText;
    private EditText language2EditText;
    private Button newVocabularyBtn;
    private LinearLayout languagesLinearLayout;
    private ArrayList<TextView> languagesTextViewList;
    //Spinner spinner;
    private Button doneBtn, deleteVocabularyBtn, sendVocabularyBtn, loadVocabularyBtn;
    private View.OnClickListener languageSelectedListener;
    int lastLanguageClicked;

    public LanguageSelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_language_selection, container, false);

        activity=(MainActivity)getActivity();
        lastLanguageClicked=-1;

        languageSelectedListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastLanguageClicked>=0){
                    languagesTextViewList.get(lastLanguageClicked).setBackgroundColor(Color.TRANSPARENT);
                }
                lastLanguageClicked=languagesTextViewList.indexOf(v);
                v.setBackgroundColor(Color.GREEN);
                String s=((TextView)v).getText().toString();
                activity.startingLanguage=s.substring(0,s.indexOf("-"));
                activity.endingLanguage=s.substring(s.indexOf("-")+1,s.length());
            }
        };

        language1EditText=(EditText)rootView.findViewById(R.id.language1EditText);
        language2EditText=(EditText)rootView.findViewById(R.id.language2EditText);
        newVocabularyBtn=(Button)rootView.findViewById(R.id.newVocabularyBtn);
        newVocabularyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newVocabulary=language1EditText.getText().toString()+"-"+language2EditText.getText().toString();
                activity.languages.add(newVocabulary);
                activity.serializeLanguageList();

                TextView temp=new TextView(activity);
                temp.setText(newVocabulary);
                temp.setClickable(true);
                temp.setOnClickListener(languageSelectedListener);
                if(languagesTextViewList.add(temp)){
                    languagesLinearLayout.addView(temp);
                }
                language1EditText.setText("");
                language2EditText.setText("");
            }
        });

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, activity.languages);
        //spinner = (Spinner)rootView.findViewById(R.id.spinner);
        //spinner.setAdapter(adapter);

        languagesLinearLayout=(LinearLayout) rootView.findViewById(R.id.languagesLinearLayout);
        languagesTextViewList=new ArrayList<TextView>();
        TextView temp;
        for(int i=0;i<activity.languages.size();i++){
            temp=new TextView(activity);
            temp.setText(activity.languages.get(i));
            temp.setClickable(true);
            temp.setOnClickListener(languageSelectedListener);
            if(languagesTextViewList.add(temp)){
                languagesLinearLayout.addView(temp);
            }
        }


        doneBtn=(Button)rootView.findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastLanguageClicked==-1){
                    Toast toast = Toast.makeText(activity, "You have to select one vocabulary!", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                //Qui cambio lingua
                if(activity.startingLanguage.length()>0 && activity.endingLanguage.length()>0){
                    activity.deserializeArchive();
                    activity.deserializeMistakes();
                    activity.deserializeCategories();

                    //Qui torno alla home
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                    //fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });

        deleteVocabularyBtn=(Button)rootView.findViewById(R.id.deleteVocabularyBtn);
        deleteVocabularyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastLanguageClicked>=0){
                    areYouSure();
                }
            }
        });

        /*
        sendVocabularyBtn=(Button)rootView.findViewById(R.id.sendVocabularyBtn);
        sendVocabularyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastLanguageClicked>=0){

                    //QUI MANDO VIA MAIL IL FILE TXT THE CREO
                    File archiveLocation = new File(activity.languages.get(lastLanguageClicked)+"_archive");
                    Uri path = Uri.fromFile(archiveLocation);
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    //set the type to 'email'
                    emailIntent .setType("text");
                    //the attachment
                    emailIntent .putExtra(Intent.EXTRA_STREAM, path);
                    startActivity(Intent.createChooser(emailIntent , "Send email..."));

                    //VERSIONE PACCO MESSA NEL TASTO "SEND ARCHIVE" IN "SETTING"
                }
            }
        });

        loadVocabularyBtn=(Button)rootView.findViewById(R.id.loadVocabularyBtn);
        loadVocabularyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //QUI DEVO CARICARE UN NUOVO VOCABOLARIO DA UN FILE
            }
        });
        */

        return rootView;
    }

    public void areYouSure(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Do you really want to delete the vocabulary '"+activity.languages.get(lastLanguageClicked)+"' ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Cancello i 3 file relativi a questo vocabolario se esistono
                        try{
                            activity.deleteFile(activity.languages.get(lastLanguageClicked)+"_archive");
                            activity.deleteFile(activity.languages.get(lastLanguageClicked)+"_mistakes");
                            activity.deleteFile(activity.languages.get(lastLanguageClicked)+"_categories");
                        }catch(Exception e){
                            //Non faccio nulla
                        }
/*                        try{
                            Toast toast = Toast.makeText(activity, "ARCHIVE FILE DELETED? "+activity.deleteFile(activity.languages.get(lastLanguageClicked)+"_archive"), Toast.LENGTH_LONG);
                            toast.show();
                        }catch(Exception e){
                            //Non faccio nulla
                            Toast toast = Toast.makeText(activity, "ARCHIVE FILE NOT DELETED", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        try{
                            Toast toast = Toast.makeText(activity, "MISTAKES FILE DELETED? "+activity.deleteFile(activity.languages.get(lastLanguageClicked)+"_mistakes"), Toast.LENGTH_LONG);
                            toast.show();
                        }catch(Exception e){
                            //Non faccio nulla
                            Toast toast = Toast.makeText(activity, "MISTAKES FILE NOT DELETED", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        try{
                            Toast toast = Toast.makeText(activity, "CATEGORIES FILE DELETED? "+activity.deleteFile(activity.languages.get(lastLanguageClicked)+"_categories"), Toast.LENGTH_LONG);
                            toast.show();
                        }catch(Exception e){
                            //Non faccio nulla
                            Toast toast = Toast.makeText(activity, "CATEGORIES FILE NOT DELETED", Toast.LENGTH_LONG);
                            toast.show();
                        }
*/
                        //Cancello il vocabolario dall'elenco dei vocabolari contenuto in activity
                        activity.languages.remove(lastLanguageClicked);
                        //Rendo invisibile il vocabolario nel linear layout
                        languagesTextViewList.get(lastLanguageClicked).setVisibility(View.GONE);
                        //Cancello il vocabolario dall'arraylist di vocabolari
                        languagesTextViewList.remove(lastLanguageClicked);
                        //Salvo quanto appena fatto
                        activity.serializeLanguageList();
                        lastLanguageClicked=-1;
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
    }



}
