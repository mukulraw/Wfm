package com.tbx.user.wfm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by USER on 11/28/2017.
 */

public class Progress extends Fragment {


    TabLayout tabs;

    ViewPager pager;

    ProgressAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress , container , false);

        tabs = (TabLayout)view.findViewById(R.id.tabs);

        pager = (ViewPager)view.findViewById(R.id.pager);

        adapter = new ProgressAdapter(getChildFragmentManager() , 3);

        tabs.addTab(tabs.newTab().setText("Daily"));
        tabs.addTab(tabs.newTab().setText("Weekly"));
        tabs.addTab(tabs.newTab().setText("Monthly"));

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);

        tabs.getTabAt(0).setText("Daily");
        tabs.getTabAt(1).setText("Weekly");
        tabs.getTabAt(2).setText("Monthly");

        return view;
    }



    public class ProgressAdapter extends FragmentStatePagerAdapter {


        public ProgressAdapter(FragmentManager fm , int tab) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            if (position == 0){

                return new Daily();
            }

            else if (position == 1){

                return new Weekly();
            }

            else if (position == 2 ){

                return new Month();
            }
            else
            {
                return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
