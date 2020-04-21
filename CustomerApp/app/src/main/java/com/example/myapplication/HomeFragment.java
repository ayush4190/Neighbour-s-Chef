package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    private TextView locationView;
    private TextView changeLocView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSectionsPagerAdapter =  new SectionsPagerAdapter(getChildFragmentManager());
        mTabLayout = (TabLayout) view.findViewById(R.id.home_tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mViewPager = (ViewPager) view.findViewById(R.id.home_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTabLayout.setScrollPosition(position, 0, true);
                mTabLayout.setSelected(true);
                mViewPager.getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        locationView = (TextView) view.findViewById(R.id.home_TV_Location);

        changeLocView = (TextView) view.findViewById(R.id.home_TV_notHere);

        return view;
    }


    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                   TodaysMenuFragment tab1 = new TodaysMenuFragment();
                    return tab1;
                case 1:
                    TomorrowMenuFragment tab2 = new TomorrowMenuFragment();
                    return tab2;
                case 2:
                    RestoftheWeekFragment tab3 = new RestoftheWeekFragment();
                    return tab3;
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Today's Menu";
                case 1:
                    return "Tomorrow's Menu ";
                case 2:
                    return "Rest of the Week ";
                default:
                    break;
            }
            return null;
        }
    }

}
