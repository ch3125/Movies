package com.me.movies;

import android.icu.text.IDNA;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



/**
 * Created by Home on 11/19/2016.
 */

public class PagerAdapter2 extends FragmentStatePagerAdapter{
    int tabCount;

    public PagerAdapter2(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DetailActivityFragment infoActivity=new DetailActivityFragment();
                return infoActivity;

            case 1:
               Cast cast =new Cast();
                return cast;
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
