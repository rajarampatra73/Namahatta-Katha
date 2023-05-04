package com.Gangasagar.namahatta_katha.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends FragmentPagerAdapter {

    private  final List<Fragment> fragmentList = new ArrayList<>();
    private  final List<String> fragmentTitle = new ArrayList<>();

    public ViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public ViewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
    public void addFragment(Fragment f, String s){
        fragmentList.add(f);
        fragmentTitle.add(s);

    }
}
