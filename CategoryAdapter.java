package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {


    public CategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0)
            return new NumbersFragment();
        else if(i == 1)
            return new FamilyMembersFragment();
        else if(i == 3)
            return new ColorsFragment();
        else
            return new PhrasesFragment();

    }

    @Override
    public int getCount() {
        return 4;
    }
}
