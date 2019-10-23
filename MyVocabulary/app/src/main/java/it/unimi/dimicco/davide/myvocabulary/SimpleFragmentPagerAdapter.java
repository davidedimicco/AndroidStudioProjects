package it.unimi.dimicco.davide.myvocabulary;


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
                return new ArchiveFragment();
            case 1:
                return new TestFragment();
            case 2:
                return new StatisticsFragment();
            case 3:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Archive";
            case 1:
                return "Test";
            case 2:
                //statistics è troppo lungo!
                return "Stats";
            case 3:
                return "Settings";
            default:
                return null;
        }
    }
}