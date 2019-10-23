package it.unimi.dimicco.davide.myvocabulary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Davide on 26/10/2017.
 */

public class Entry implements Serializable {

    private String language1Word;
    private String language2Word;
    //int weight1to2;
    //int weight2to1;
    //int right1to2;
    //int right2to1;
    //int wrong1to2;
    //int wrong2to1;

    public Entry(String s1,String s2){
        language1Word=s1;
        language2Word=s2;
    }

    public String getFirstWord(){
        return language1Word;
    }

    public String getSecondWord(){
        return language2Word;
    }

    public static ArrayList<String> getImages(ArrayList<Entry> couples, String word){
        ArrayList<String> images = new ArrayList<String>();
        for (Entry entry:couples) {
            if(word.equals(entry.getFirstWord())){
                images.add(entry.getSecondWord());
            }
        }
        return images;
    }

    public static ArrayList<String> getPreImages(ArrayList<Entry> couples, String word){
        ArrayList<String> preImages = new ArrayList<String>();
        for (Entry entry:couples) {
            if(word.equals(entry.getSecondWord())){
                preImages.add(entry.getFirstWord());
            }
        }
        return preImages;
    }
    
    //Translations will be written as same/equal/equivalent
    public static ArrayList<Entry> parse(String word,String translations){
        ArrayList<Entry> couples = new ArrayList<Entry>();
        String meaning;
        while(translations.contains("/")){
            meaning=translations.substring(0,translations.indexOf("/"));
            couples.add(new Entry(word,meaning));
            translations=translations.substring(translations.indexOf("/")+1,translations.length());
        }
        couples.add(new Entry(word,translations));
        return couples;
    }

    //To add words from text file
    public static ArrayList<Entry> fromTxtToArray(String filename) throws IOException{
        ArrayList<Entry> couples = new ArrayList<Entry>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        String word, translations;
        while (line != null) {
            word = line.substring(0,line.indexOf("="));
            translations = line.substring(line.indexOf("=")+1,line.length());
            couples.addAll(Entry.parse(word,translations));
            line = br.readLine();
        }
        br.close();
        return couples;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (!language1Word.equals(entry.language1Word)) return false;
        return language2Word.equals(entry.language2Word);

    }

    @Override
    public int hashCode() {
        int result = language1Word.hashCode();
        result = 31 * result + language2Word.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "\n"+language1Word+" = "+language2Word;
    }
}
