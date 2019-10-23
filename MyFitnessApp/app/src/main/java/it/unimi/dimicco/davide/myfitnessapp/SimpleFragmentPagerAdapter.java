package it.unimi.dimicco.davide.myfitnessapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Davide on 01/09/2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TapCounterFragment();
            case 1:
                return new TimerFragment();
            case 2:
                return new ChronometerFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Tap Counter";
            case 1:
                return "Timer";
            case 2:
                //chronometer è troppo lungo!
                return "Chrono";
            default:
                return null;
        }
    }
}