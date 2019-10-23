package it.unimi.dimicco.davide.mypersonalvocabulary;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Davide on 19/11/2017.
 */

public class Category implements Serializable, Comparable<Category>{

    private String name;

    private ArrayList<Entry> listOfEntries;

    public Category(String nameOfCategory){
        name=nameOfCategory;
        listOfEntries=new ArrayList<Entry>();
    }

    //Qui faccio in modo che nelle categorie non vengano ripetute le entrate
    public boolean addEntry(Entry e){
        if(this.containsEntry(e)){
            return false;
        }
        return (this.listOfEntries.add(e));
    }

    public boolean removeEntry(Entry e){
        return(this.listOfEntries.remove(e));
    }

    public boolean containsEntry(Entry e){
        return(this.listOfEntries.contains(e));
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name=newName;
    }

    public ArrayList<Entry> getEntries(){
        return listOfEntries;
    }

    @Override
    public int compareTo(Category category) {
        return this.getName().compareTo(category.getName());
    }

    @Override
    public String toString(){
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return getName().equals(category.getName());

    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
