package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Davide on 19/11/2017.
 */

public class MenuFragment extends Fragment {

    //private MainActivity activity;
    private Button addWordsBtn;
    private Button testsBtn;
    private Button statisticsBtn;
    //private Button settingsBtn;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        addWordsBtn=(Button)rootView.findViewById(R.id.addWordsBtn);

        addWordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the addWords fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new AddWordsFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        testsBtn=(Button)rootView.findViewById(R.id.testsBtn);
        testsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the test preparation fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new TestPreparationFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        statisticsBtn=(Button)rootView.findViewById(R.id.statisticsBtn);
        statisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I want to open the statistics fragment
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new StatisticsFragment());
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootView;
    }



}
