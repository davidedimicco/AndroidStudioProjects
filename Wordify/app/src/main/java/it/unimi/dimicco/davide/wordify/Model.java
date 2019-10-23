package it.unimi.dimicco.davide.wordify;

/**
 * Created by Davide on 24/04/2018.
 */

public class Model {

    /*Rappresentazione dei 9 dadi*/
    public static final String[] dadi={"ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF","ABCDEF"};

    /*rappresentazione delle 9 lettere estratte*/
    public String esito;

    public Model(){
        esito="";
        for(int i=0;i<10;i++){
            esito+=dadi[i].charAt((int)(Math.floor(Math.random()*6)));
        }
    }

}
