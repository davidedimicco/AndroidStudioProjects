package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Davide on 19/11/2017.
 */

public class SettingsFragment extends Fragment {

    private MainActivity activity;
    private Button languagesBtn;
    private Button wordsBtn;
    private Button categoriesBtn;
    private Button sendArchiveBtn;
    private Button loadArchiveBtn;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        activity=(MainActivity)getActivity();

        languagesBtn=(Button)rootView.findViewById(R.id.languagesBtn);
        languagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I save the current language files (archive, mistakes and categories)
                activity.serializeArchive();
                activity.serializeMistakes();
                activity.serializeCategories();
                //Here I want to open the language-settings fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new LanguageSelectionFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        wordsBtn=(Button)rootView.findViewById(R.id.wordsBtn);
        wordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the word-setting fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new WordSettingsFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        categoriesBtn=(Button)rootView.findViewById(R.id.categoriesBtn);
        categoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the categories-setting fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new CategoryModifierFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        sendArchiveBtn=(Button)rootView.findViewById(R.id.sendArchiveBtn);
        sendArchiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(activity.categories);
                Collections.sort(activity.archive);
                //Versione pacco del modo di mandare Mi mando via mail un file di testo costruito scrivendo sia archivio che categorie, e poi quando cambio telefono faccio in modo che se all'avvio dell'app non c'è un file JAP-ITA, lo carico dal file di testo
                String text="";
                for(int i=0;i<activity.archive.size();i++){
                    text+=activity.archive.get(i).toStringForArchive()+"{";
                    String temp="";
                    for(int j=0;j<activity.categories.size();j++){
                        if(activity.categories.get(j).containsEntry(activity.archive.get(i))){
                            temp+=activity.categories.get(j).getName()+",";
                        }
                    }
                    if(!temp.equals("")){
                        temp=temp.substring(0,temp.length()-1);
                    }
                    text+=temp+"}\n";
                }
                //QUA DEVO PRODURRE UN FILE DI TESTO CHE ABBIA COME TESTO LA STRINGA text

                //QUA DEVO APRIRE UNA MAIL IN CUI CI SARà COME ALLEGATO IL FILE DI TESTO APPENA CREATO
                //WHATSAPP
                /*
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    waIntent.setPackage("com.whatsapp");
                    if (waIntent != null) {
                        waIntent.putExtra(Intent.EXTRA_TEXT, text);//
                        startActivity(Intent.createChooser(waIntent, "Share with"));
                    } else {
                        Toast.makeText(activity, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                                .show();
                    }} catch (Exception e) {
                    Toast.makeText(activity, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
                */
                //MAIL
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Personal Vocabulary ("+activity.startingLanguage+"-"+activity.endingLanguage+")");
                emailIntent.putExtra(Intent.EXTRA_TEXT, text);
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadArchiveBtn=(Button)rootView.findViewById(R.id.loadArchiveBtn);
        loadArchiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the ArchiveLoaderFragment fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new ArchiveLoaderFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }

}
