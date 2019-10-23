package it.unimi.dimicco.davide.myvocabulary;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Davide on 17/11/2017.
 */

public class Category implements Serializable {

    private String name;

    private ArrayList<Integer> listOfIndices;

    public Category(String nameOfCategory){
        name=nameOfCategory;
        listOfIndices=new ArrayList<Integer>();
    }

    public boolean addIndex(Integer n){
        return(this.listOfIndices.add(n));
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Integer> getList(){
        return listOfIndices;
    }

    //voglio riordinare le categorie in modo che siano in ordine alfabetico
    public void sort(){
        //DAFARE!!
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
