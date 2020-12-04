package com.aditya.news.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aditya.news.R;
import com.aditya.news.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends Fragment {
    TabLayout tabLayout;
    TabItem tablatest,tabworlds,tabbusiness,tabtechnology;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabLayout=getActivity().findViewById(R.id.tablayout);
        viewPager=getActivity().findViewById(R.id.viewpager);
        tablatest=getActivity().findViewById(R.id.latest);
        tabworlds=getActivity().findViewById(R.id.worlds);
        tabbusiness=getActivity().findViewById(R.id.Business);
        tabtechnology=getActivity().findViewById(R.id.Technology);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);
//        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getContext(),getActivity().getSupportFragmentManager());
//        viewPager.setAdapter(viewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tablayout);
        tabs.setupWithViewPager(viewPager);

//        viewPagerAdapter=new ViewPagerAdapter(getActivity().getApplicationContext(),getActivity().getSupportFragmentManager(),tabLayout.getTabCount());
//        viewPager.setAdapter(viewPagerAdapter);
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        return  view;
    }


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new Latest(), "Latest");
        adapter.addFragment(new Worlds(), "world");
        adapter.addFragment(new Business(), "Business");
        adapter.addFragment(new Technology(), "Techology");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4); //So they will not be created when you move from tab


    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}