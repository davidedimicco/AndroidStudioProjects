package it.unimi.dimicco.davide.mypersonalvocabulary;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Davide on 19/11/2017.
 */

public class HomeFragment extends Fragment {

    private MainActivity activity;
    SimpleFragmentPagerAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        activity=(MainActivity)getActivity();

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager=(ViewPager)rootView.findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        //Lo sposto fuori e lo dichiaro dentro, in modo da poterlo vedere da fuori, ma non so se è giusto
        adapter = new SimpleFragmentPagerAdapter(getChildFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

}
