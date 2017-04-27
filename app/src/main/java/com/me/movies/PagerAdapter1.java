package com.me.movies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by Home on 11/18/2016.
 */

public class PagerAdapter1 extends FragmentStatePagerAdapter {
    int tabCount;

    public PagerAdapter1(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Upcoming upcoming=new Upcoming();
                return upcoming;
            case 1:
               NowPlaying nowPlaying=new NowPlaying();
                return nowPlaying;
            case 2:
                Popular popular=new Popular();
                return popular;
            case 3:
                MoviesFragment moviesFragment=new MoviesFragment();
                return moviesFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
