package com.example.moneymanager.setting;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moneymanager.R;

import java.util.ArrayList;

public class ViewPagerAdapter  extends FragmentPagerAdapter {

    private Context mContext;
    private ArrayList<Fragment>mListFragment;
    public ViewPagerAdapter (FragmentManager fm, Context mContext, ArrayList<Fragment>mListFragment){
        super(fm);
        this.mContext = mContext;
        this.mListFragment = mListFragment;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return mContext.getString(R.string.expenses).toUpperCase();
        }
        else
            return mContext.getString(R.string.income).toUpperCase();
    }
}
