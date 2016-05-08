package com.monitoringsiswa.monitoringguru.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.monitoringsiswa.monitoringguru.ui.fragment.HomeFragment;
import com.monitoringsiswa.monitoringguru.ui.fragment.InputPelanggaranFragment;


/**
 * Created by root on 21/03/16.
 */
public class TabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    HomeFragment tab1 = new HomeFragment();

    public TabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return tab1;
            case 1:
                InputPelanggaranFragment tab2 = InputPelanggaranFragment.newInstance(tab1);
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
