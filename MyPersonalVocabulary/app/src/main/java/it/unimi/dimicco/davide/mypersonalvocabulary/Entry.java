package it.unimi.dimicco.davide.mypersonalvocabulary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Davide on 19/11/2017.
 */

public class Entry implements Serializable, Comparable<Entry>{

    private String language1Word;
    private String transliterationWord;
    private String language2Word;

    public Entry(String s1,String t, String s2){
        language1Word=s1.trim();
        transliterationWord=t.trim();
        language2Word=s2.trim();
    }

    public String getWord(){
        return language1Word;
    }

    public String getTransliteration(){
        return transliterationWord;
    }

    public String getTranslation(){
        return language2Word;
    }

    public void setWord(String word){
        language1Word=word.trim();
    }

    public void setTransliteration(String translit){
        transliterationWord=translit.trim();
    }

    public void setTranslation(String translation){
        language2Word=translation.trim();
    }

    //IMPROVEMENT OF THE FOLLOWING METHODS
    public static ArrayList<String> getTranslationsFromWord(ArrayList<Entry> triples, String word){
        ArrayList<String> translations = new ArrayList<String>();
        for (Entry entry:triples) {
            if(word.equals(entry.getWord())){
                translations.add(entry.getTranslation());
            }
        }
        return translations;
    }

    public static ArrayList<String> getTranslationsFromTransliteration(ArrayList<Entry> triples, String transliteratedWord){
        ArrayList<String> translations = new ArrayList<String>();
        for (Entry entry:triples) {
            if(transliteratedWord.equals(entry.getTransliteration())){
                translations.add(entry.getTranslation());
            }
        }
        return translations;
    }

    public static ArrayList<String> getTransliterationsFromWord(ArrayList<Entry> triples, String word){
        ArrayList<String> transliterations = new ArrayList<String>();
        for (Entry entry:triples) {
            if(word.equals(entry.getWord())){
                transliterations.add(entry.getTransliteration());
            }
        }
        return transliterations;
    }

    public static ArrayList<String> getTransliterationsFromTranslation(ArrayList<Entry> triples, String translation){
        ArrayList<String> transliterations = new ArrayList<String>();
        for (Entry entry:triples) {
            if(translation.equals(entry.getTranslation())){
                transliterations.add(entry.getTransliteration());
            }
        }
        return transliterations;
    }

    public static ArrayList<String> getWordsFromTranslation(ArrayList<Entry> triples, String translation){
        ArrayList<String> words = new ArrayList<String>();
        for (Entry entry:triples) {
            if(translation.equals(entry.getTranslation())){
                words.add(entry.getWord());
            }
        }
        return words;
    }

    public static ArrayList<String> getWordsFromTransliteration(ArrayList<Entry> triples, String transliteratedWord){
        ArrayList<String> words = new ArrayList<String>();
        for (Entry entry:triples) {
            if(transliteratedWord.equals(entry.getTransliteration())){
                words.add(entry.getWord());
            }
        }
        return words;
    }
    //END OF NEW STUFF

    //THIS METHOD DEPENDS ON THE IDEA BEHIND THE TESTS
    /*
    public static ArrayList<String> getImages(ArrayList<Entry> triples, Entry variable){
        ArrayList<String> images = new ArrayList<String>();
        for (Entry entry:triples) {
            if(variable.language1Word.equals(entry.getFirstWord())&&variable.transliterationWord.equals(entry.getTransliteration())){
                images.add(entry.getSecondWord());
            }
        }
        return images;
    }

    public static ArrayList<String> getPreImages(ArrayList<Entry> triples, Entry variable){
        ArrayList<String> preImages = new ArrayList<String>();
        for (Entry entry:triples) {
            if(variable.getSecondWord().equals(entry.getSecondWord())){
                //HERE I'M FIXING THAT THE PREIMAGE IS GIVEN BY THE TRANSLITERATION.. IT HAS TO BE FIXED DEPENDING ON THE AIM!
                preImages.add(entry.getTransliteration());
            }
        }
        return preImages;
    }
    */

    //Translations will be written as "same,equal,equivalent"
    public static ArrayList<Entry> parse(String word,String transliteration,String translations){
        ArrayList<Entry> triples = new ArrayList<Entry>();
        String meaning;
        while(translations.contains(",")){
            meaning=translations.substring(0,translations.indexOf(","));
            triples.add(new Entry(word,transliteration,meaning));
            translations=translations.substring(translations.indexOf(",")+1,translations.length());
        }
        triples.add(new Entry(word,transliteration,translations));
        return triples;
    }

    //To add words from text file in which lines are of the form "word <> transliteration = translation"
    public static ArrayList<Entry> fromTxtToArray(String filename) throws IOException{
        ArrayList<Entry> triples = new ArrayList<Entry>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        String word, transliteration, translation;
        while (line != null) {
            word = line.substring(0,line.indexOf("<"));
            transliteration = line.substring(line.indexOf(">")+1,line.indexOf("="));
            translation = line.substring(line.indexOf("=")+1,line.length());
            triples.add(new Entry(word,transliteration,translation));
            line = br.readLine();
        }
        br.close();
        return triples;
    }

    //CHECK WHAT HAPPENS HERE! I'M NOT SURE WHAT'S THE IDEA BEHIND THIS METHOD
    @Override
    public int compareTo(Entry entry) {
        return (this.transliterationWord.toLowerCase().compareTo(entry.transliterationWord.toLowerCase())!=0)? this.transliterationWord.toLowerCase().compareTo(entry.transliterationWord.toLowerCase()) : this.language2Word.toLowerCase().compareTo(entry.language2Word.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        //if (!language1Word.equals(entry.language1Word)) return false;
        if (!transliterationWord.equals(entry.transliterationWord)) return false;
        return language2Word.equals(entry.language2Word);

    }

    @Override
    public int hashCode() {
        int result = transliterationWord.hashCode();
        result = 31 * result + language2Word.hashCode();
        return result;
    }

    //"word (transliteration) = translation" or "word = translation"
    @Override
    public String toString() {
        if(language1Word.equals("")){
            return transliterationWord+" = "+language2Word;
        }
        return language1Word+" ("+transliterationWord+") = "+language2Word;

    }

    //"word <> transliteration = translation"
    public String toStringForArchive() {
        return language1Word+" <> "+transliterationWord+" = "+language2Word;
    }
}
