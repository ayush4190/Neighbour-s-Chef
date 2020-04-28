package com.example.myapplication.ui.activity.login;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.ui.fragment.login.SignInFragment;
import com.example.myapplication.ui.fragment.register.SignUpFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    SectionsPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SignInFragment.newInstance();
            case 1:
                return SignUpFragment.newInstance();
            default:
                throw new IllegalArgumentException("Position " + position + " is out of bounds!");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Sign in";
            case 1:
                return "Sign up";
        }
        return null;
    }
}
