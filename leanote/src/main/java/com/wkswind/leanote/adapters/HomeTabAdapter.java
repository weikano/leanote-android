package com.wkswind.leanote.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wkswind.leanote.home.RecentNotesFragment;

/**
 * Created by Administrator on 2016-12-21.
 */

public class HomeTabAdapter extends FragmentPagerAdapter {
    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new RecentNotesFragment();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "test";
    }
}
