package it.unimi.dimicco.davide.myfitnessapp;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 */
public class TapCounterFragment extends Fragment {


    private int counter;

    private Button resetBtn;

    private Button untapBtn;

    private Button tapBtn;

    public TapCounterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tap_counter, container, false);

        resetBtn=(Button)rootView.findViewById(R.id.resetBtn);
        untapBtn=(Button)rootView.findViewById(R.id.untapBtn);
        tapBtn=(Button)rootView.findViewById(R.id.tapBtn);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //faccio comparire un menu che mi chiede se voglio davvero resettare o se voglio annullare
                areYouSure();
            }
        });

        untapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter>0) {
                    counter--;
                    refresh();
                }
            }
        });

        tapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                refresh();
            }
        });

        refresh();
        return rootView;
    }

    public void refresh(){
        tapBtn.setText(counter+"");
    }

    public void areYouSure(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage("Reset counter?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        counter=0;
                        refresh();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
