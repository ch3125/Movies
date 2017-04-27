package com.me.movies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Home on 11/20/2016.
 */

public class PagerAdapter3 extends FragmentStatePagerAdapter {
    int tabCount;

    public PagerAdapter3(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DetailCastFragment infoActivity=new DetailCastFragment();
                return infoActivity;

            case 1:
                Credits credits =new Credits();
                return credits;
            case 2:
                OverView overView=new OverView();
                return overView;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
