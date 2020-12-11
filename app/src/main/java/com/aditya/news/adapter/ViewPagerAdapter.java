package com.aditya.news.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aditya.news.fragment.Business;
import com.aditya.news.fragment.Latest;
import com.aditya.news.fragment.Technology;
import com.aditya.news.fragment.Worlds;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int tabcount;
    private Context mcontext;

    //    public ViewPagerAdapter(@NonNull FragmentManager fm) {
//        super(fm);
//    }
//
//    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//    }
    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mcontext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Latest();
            case 1:
                return new Worlds();
            case 2:
                return new Business();
            case 3:
                return new Technology();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {

        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Latest";
        } else if (position == 1) {
            title = "Worlds";
        } else if (position == 2) {
            title = "Business";
        } else {
            title = "Technology";
        }
        return title;
    }
}
