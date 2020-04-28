package com.example.myapplication.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.util.android.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        binding.homeTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        binding.homePager.setAdapter(adapter);
        binding.homeTabLayout.setupWithViewPager(binding.homePager);
        binding.homePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                binding.homeTabLayout.setScrollPosition(position, 0, true);
                binding.homeTabLayout.setSelected(true);
                binding.homePager.getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}