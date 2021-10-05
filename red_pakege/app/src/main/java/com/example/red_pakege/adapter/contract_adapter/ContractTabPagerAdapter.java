package com.example.red_pakege.adapter.contract_adapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * created  by ganzhe on 2019/11/22.
 */
public class ContractTabPagerAdapter extends FragmentPagerAdapter {

    String[] titles;
    Fragment[] fragments;

    public ContractTabPagerAdapter(@NonNull FragmentManager fm,String[] titles,Fragment[] fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return null ==titles ? 0:titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
