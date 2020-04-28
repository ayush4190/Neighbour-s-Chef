package com.example.myapplication.ui.fragment.home;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.ui.fragment.menu.today.TodaysMenuFragment;
import com.example.myapplication.ui.fragment.menu.tomorrow.TomorrowMenuFragment;
import com.example.myapplication.ui.fragment.menu.restoftheweek.RestoftheWeekFragment;

class SectionsPagerAdapter extends FragmentPagerAdapter {
    SectionsPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TodaysMenuFragment.newInstance();
            case 1:
                return TomorrowMenuFragment.newInstance();
            case 2:
                return RestoftheWeekFragment.newInstance();
            default:
                throw new IllegalArgumentException("Position " + position + " is out of bounds!");
        }
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
