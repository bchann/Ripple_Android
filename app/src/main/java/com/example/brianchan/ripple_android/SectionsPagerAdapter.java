package com.example.brianchan.ripple_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Parikshit on 3/5/17.
 */

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * How to add fragments to tabs.
     * @param position Tab location - 1
     * @return The fragment that fills the position.
     */
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if (position == 0 || position == 2) {
            return SonglistFragment.newInstance();
        }

        ArrayList<Song> songList= new ArrayList();

        return RequestsFragment.newInstance();
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Playlist";
            case 1:
                return "Requests";
            case 2:
                return "History";
        }
        return null;
    }
}