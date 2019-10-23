package it.unimi.dimicco.davide.myvocabulary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Davide on 18/11/2017.
 */

public class SettingsFragment extends Fragment{

    private MainActivity activity;

    private Spinner spinner;

    private ArrayAdapter<String> adapter;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_settings, container, false);

        activity=(MainActivity)getActivity();
        spinner=(Spinner)rootView.findViewById(R.id.categorySpinner);


        adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item);

        ArrayList<String> categoriesNames=new ArrayList<String>();
        for(int i=0;i<activity.categories.size();i++){
            categoriesNames.add(activity.categories.get(i).getName());
            adapter.add(activity.categories.get(i).getName());
        }

        /*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item,categoriesNames);
        // Specify the layout to use when the list of choices appears
        */


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

/*
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
*/


        return rootView;
    }



}
