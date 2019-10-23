package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    TextView statisticsTextView;
    MainActivity activity;
    Button statsBtn;
    Button frequentMistakesBtn;
    Button homeBtn3;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        statisticsTextView=(TextView)rootView.findViewById(R.id.statisticsTextView);
        activity=(MainActivity)getActivity();
        statsBtn=(Button)rootView.findViewById(R.id.lastTestScoreBtn);
        frequentMistakesBtn=(Button)rootView.findViewById(R.id.frequentMistakesBtn);
        homeBtn3=(Button)rootView.findViewById(R.id.homeBtn3);

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsTextView.setText(activity.statistics);
            }
        });

        frequentMistakesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="";
                //MODIFICATO NON DA SANISSIMO..
                ArrayList<Entry> frequentMistakes=new ArrayList<Entry>();
                int[] multiplicities=new int[activity.mistakes.size()];
                int indexOfMistake;
                for(int i=0;i<activity.mistakes.size();i++){
                    indexOfMistake=frequentMistakes.indexOf(activity.mistakes.get(i));
                    if (indexOfMistake>=0){
                        multiplicities[indexOfMistake]++;
                    }
                    else{
                        frequentMistakes.add(activity.mistakes.get(i));
                        multiplicities[frequentMistakes.size()-1]=1;
                    }
                }
                for(int i=0;i<frequentMistakes.size();i++){
                    s+=frequentMistakes.get(i).toString();
                    if(multiplicities[i]>1){
                        s+="(x"+multiplicities[i]+")";
                    }
                    s+="\n";
                }
                statisticsTextView.setText(s);
            }
        });

        homeBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the home fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return rootView;
    }



}
