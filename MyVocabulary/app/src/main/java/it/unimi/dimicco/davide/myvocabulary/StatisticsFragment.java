package it.unimi.dimicco.davide.myvocabulary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    TextView statisticsTextView;
    ScrollView scrollView;
    MainActivity activity;
    Button statsBtn, archiveBtn, mistakesBtn;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_statistics, container, false);

        statisticsTextView=(TextView)rootView.findViewById(R.id.statisticsTextView);
        activity=(MainActivity)getActivity();
        statsBtn=(Button)rootView.findViewById(R.id.statsBtn);
        archiveBtn=(Button)rootView.findViewById(R.id.archiveBtn);
        mistakesBtn=(Button)rootView.findViewById(R.id.mistakesBtn);

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsTextView.setText(activity.statistics);
            }
        });

        archiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsTextView.setText(activity.archive.toString());
            }
        });

        mistakesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statisticsTextView.setText(activity.mistakes.toString());
            }
        });

        return rootView;
    }



}
