package com.nikita.calculator;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ControlsAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;

    public ControlsAdapter(final FragmentManager fragmentManager, final List<Fragment> fragments) {
        super(fragmentManager);
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(final int position) {
        if (position >= 0 && position < mFragments.size()) {
            return mFragments.get(position);
        }
        return null;
    }
}
