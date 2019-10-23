package com.example.utente.programmino;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainActivity activity=this;
        Button addValuesBtn=(Button)findViewById(R.id.addSingleValueBtn);
        Button addPartialsBtn=(Button)findViewById(R.id.addPartialBtn);
        Button computeBtn=(Button)findViewById(R.id.computeBtn);
        Button resetBtn=(Button)findViewById(R.id.resetBtn);
        final EditText valuesEditText=(EditText)findViewById(R.id.singleValueEditText);
        final EditText partialsEditText=(EditText)findViewById(R.id.partialsEditText);
        final TextView valuesTextView=(TextView)findViewById(R.id.valuesTextView);
        final TextView partialsTextView=(TextView)findViewById(R.id.partialsTextView);
        final TextView outputTextView=(TextView)findViewById(R.id.outputTextView);
        final ArrayList<BigDecimal> values=new ArrayList<BigDecimal>();
        final ArrayList<BigDecimal> partials=new ArrayList<BigDecimal>();
        addValuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(valuesEditText.getText().toString()=="")
                    return;
                values.add(new BigDecimal(valuesEditText.getText().toString()));
                valuesTextView.setText(valuesTextView.getText().toString()+"\n"+(values.get(values.size()-1)).toString());
                valuesEditText.setText("");
            }
        });
        addPartialsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partialsEditText.getText().toString()=="")
                    return;
                partials.add(new BigDecimal(partialsEditText.getText().toString()));
                partialsTextView.setText(partialsTextView.getText().toString()+"\n"+(partials.get(partials.size()-1)).toString());
                partialsEditText.setText("");
            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values.clear();
                partials.clear();
                valuesTextView.setText("");
                valuesEditText.setText("");
                partialsTextView.setText("");
                partialsEditText.setText("");
                outputTextView.setText("");
            }
        });
        computeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n=values.size();
                int[] partition=new int[n];
                int m=partials.size();
                String solution="";
                Collections.sort(values,Collections.<BigDecimal>reverseOrder());
                Collections.sort(partials);
                /*
                for (int i=0;i<n;i++){
                    partition[i]=0;
                }
                while(true){
                    if(isCorrect(values,partials,partition)){
                        solution=outputTextView.getText().toString();
                        if(solution!=""){
                            solution+="\n"+"oppure"+"\n\n";
                        }
                        for(int k=0;k<m;k++){
                            solution+=(partials.get(k)).toString()+"=";
                            for(int l=0;l<n;l++){
                                if(partition[l]==k){
                                    solution+=(values.get(l)).toString()+"+";
                                }
                            }
                            solution=solution.substring(0,solution.lastIndexOf("+"))+"\n";
                        }
                        outputTextView.setText(solution);
                    }
                    try{
                        nextStep(n,m,partition);
                    }catch (IllegalStateException e){
                        if(outputTextView.getText().toString()==""){
                            outputTextView.setText("Non ci sono soluzioni, prova a controllare i numeri!");
                        }
                        return;
                    }
                }
                */
                for(int i=0;i<m;i++){
                    for (int j=0;j<values.size();j++){
                        partition[j]=0;
                    }
                    while(true){
                        if(isQuasiCorrect(values,partials.get(i),partition)){
                            solution+=partials.get(i).toString()+"=";
                            for (int k=0;k<values.size();k++){
                                if(partition[k]==1){
                                    solution+=values.get(k).toString()+"+";
                                }
                            }
                            solution=solution.substring(0,solution.lastIndexOf("+"))+"\n";
                            //Be careful here!!!
                            outputTextView.setText(solution);
                            for (int k=values.size()-1;k>=0;k--){
                                if(partition[k]==1){
                                    values.remove(k);
                                }
                            }
                            break;
                        }
                        try{
                            nextBinaryStep(values.size(),partition);
                        }catch (IllegalStateException e){
                            if(outputTextView.getText().toString()==""){
                                outputTextView.setText("Non ci sono soluzioni (non è detto..), prova a controllare i numeri!");
                            }
                            return;
                        }
                    }
                }
                outputTextView.setText(solution);
            }
        });
    }

    public boolean isCorrect(ArrayList<BigDecimal> input, ArrayList<BigDecimal> sums, int[]coefficients){
        BigDecimal tot;
        for(int i=0;i<sums.size();i++){
            tot=new BigDecimal(0);
            for(int j=0;j<coefficients.length;j++){
                if(coefficients[j]==i){
                    tot=tot.add(input.get(j));
                }
            }
            if(tot.compareTo(sums.get(i))!=0){
                return false;
            }
        }
        return true;
    }

    public boolean isQuasiCorrect(ArrayList<BigDecimal> input, BigDecimal sum, int[]coefficients){
        BigDecimal tot=new BigDecimal(0);
        for(int j=0;j<input.size();j++){
            if(coefficients[j]==1){
                tot=tot.add(input.get(j));
            }
        }
        return tot.compareTo(sum)==0;
    }

    public void nextStep(int x,int y,int[] coefficients) throws IllegalStateException{
        for(int j=0;j<x;j++){
            if(coefficients[j]<(y-1)){
                coefficients[j]++;
                return;
            }
            else{
                coefficients[j]=0;
                if(j==x-1){
                    throw new IllegalStateException();
                }
            }
        }
        return;
    }

    public void nextBinaryStep(int x,int[] coefficients) throws IllegalStateException{
        for(int j=0;j<x;j++){
            if(coefficients[j]==0){
                coefficients[j]++;
                return;
            }
            else{
                coefficients[j]=0;
                if(j==x-1){
                    throw new IllegalStateException();
                }
            }
        }
        return;
    }

}