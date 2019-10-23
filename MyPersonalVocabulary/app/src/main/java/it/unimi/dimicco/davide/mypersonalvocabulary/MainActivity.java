package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Entry> archive;
    ArrayList<Entry> mistakes;
    ArrayList<Entry> archiveForTest;
    ArrayList<Entry> mistakesForTest;
    ArrayList<Category> categories;
    ArrayList<String> languages;
    String startingLanguage;
    String endingLanguage;
    boolean fromLanguage1toLanguage2;
    String statistics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        startingLanguage="";
        endingLanguage="";
        this.deserializeLanguageList();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frameLayout,new LanguageSelectionFragment());
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void deserializeLanguageList(){
        try {
            FileInputStream fis=this.openFileInput("Languages");
            ObjectInputStream ois=new ObjectInputStream(fis);
            this.languages=(ArrayList<String>)ois.readObject();
            ois.close();
            fis.close();
            //Toast toast = Toast.makeText(this, "LANGUAGES FOUND!", Toast.LENGTH_LONG);
            //toast.show();
        } catch (ClassNotFoundException | IOException e1) {
            //se l'archivio ancora non esiste lo creo
            languages = new ArrayList<String>();
            //Toast toast = Toast.makeText(this, "Languages file not found!", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void serializeLanguageList(){
        FileOutputStream fos;
        try {
            //fos = new FileOutputStream(((MainActivity)getActivity()).path+((MainActivity)getActivity()).startingLanguage+"-"+((MainActivity)getActivity()).endingLanguage);
            fos = this.openFileOutput("Languages", Context.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(this.languages);
            oos.close();
            fos.close();
            //Toast toast = Toast.makeText(this, "LANGUAGES WRITTEN!", Toast.LENGTH_LONG);
            //toast.show();
        } catch (IOException e) {
            //Toast toast = Toast.makeText(this,"Languages file not written", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void deserializeArchive(){
        try {
            FileInputStream fis=this.openFileInput(this.startingLanguage+"-"+this.endingLanguage+"_archive");
            ObjectInputStream ois=new ObjectInputStream(fis);
            this.archive=(ArrayList<Entry>)ois.readObject();
            ois.close();
            fis.close();
            //Toast toast = Toast.makeText(this, "ARCHIVE FOUND!", Toast.LENGTH_LONG);
            //toast.show();
        } catch (ClassNotFoundException | IOException e1) {
            //se l'archivio ancora non esiste lo creo
            archive = new ArrayList<Entry>();
            //Toast toast = Toast.makeText(this, "Archive file not found!", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void serializeArchive(){
        FileOutputStream fos;
        try {
            //fos = new FileOutputStream(((MainActivity)getActivity()).path+((MainActivity)getActivity()).startingLanguage+"-"+((MainActivity)getActivity()).endingLanguage);
            fos = this.openFileOutput(this.startingLanguage+"-"+this.endingLanguage+"_archive", Context.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(this.archive);
            oos.close();
            fos.close();
            //Toast toast = Toast.makeText(this, "ARCHIVE WRITTEN", Toast.LENGTH_LONG);
            //toast.show();
        } catch (IOException e) {
            //Toast toast = Toast.makeText(this,"Archive file not written", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void deserializeMistakes(){
        try {
            FileInputStream fis=this.openFileInput(this.startingLanguage+"-"+this.endingLanguage+"_mistakes");
            ObjectInputStream ois=new ObjectInputStream(fis);
            this.mistakes=(ArrayList<Entry>)ois.readObject();
            ois.close();
            fis.close();
            //Toast toast = Toast.makeText(this, "MISTAKES FOUND!", Toast.LENGTH_LONG);
            //toast.show();
        } catch (ClassNotFoundException | IOException e1) {
            //se l'archivio ancora non esiste lo creo
            mistakes = new ArrayList<Entry>();
            //Toast toast = Toast.makeText(this, "Mistakes file not found!", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void serializeMistakes(){
        FileOutputStream fos;
        try {
            //fos = new FileOutputStream(((MainActivity)getActivity()).path+((MainActivity)getActivity()).startingLanguage+"-"+((MainActivity)getActivity()).endingLanguage);
            fos = this.openFileOutput(this.startingLanguage+"-"+this.endingLanguage+"_mistakes", Context.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(this.mistakes);
            oos.close();
            fos.close();
            //Toast toast = Toast.makeText(this, "MISTAKES WRITTEN!", Toast.LENGTH_LONG);
            //toast.show();
        } catch (IOException e) {
            //Toast toast = Toast.makeText(this,"Mistakes file not written", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void deserializeCategories(){
        try {
            FileInputStream fis=this.openFileInput(this.startingLanguage+"-"+this.endingLanguage+"_categories");
            ObjectInputStream ois=new ObjectInputStream(fis);
            this.categories=(ArrayList<Category>)ois.readObject();
            ois.close();
            fis.close();
            //Toast toast = Toast.makeText(this, "CATEGORIES FOUND!", Toast.LENGTH_LONG);
            //toast.show();
        } catch (ClassNotFoundException | IOException e1) {
            //se l'archivio ancora non esiste lo creo
            this.categories = new ArrayList<Category>();
            //Toast toast = Toast.makeText(this, "Categories file not found!", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

    public void serializeCategories(){
        FileOutputStream fos;
        try {
            fos = this.openFileOutput(this.startingLanguage+"-"+this.endingLanguage+"_categories", Context.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(this.categories);
            oos.close();
            fos.close();
            //Toast toast = Toast.makeText(this, "CATEGORIES WRITTEN!", Toast.LENGTH_LONG);
            //toast.show();
        } catch (IOException e) {
            //Toast toast = Toast.makeText(this,"Categories file not written", Toast.LENGTH_LONG);
            //toast.show();
        }
    }

}
