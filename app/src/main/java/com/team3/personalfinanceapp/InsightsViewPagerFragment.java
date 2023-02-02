package com.team3.personalfinanceapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team3.personalfinanceapp.adapter.InsightsViewPagerAdapter;

public class InsightsViewPagerFragment extends Fragment {


    private ViewPager2 viewPager;

    private FragmentStateAdapter insightsPagerAdapter;


    public InsightsViewPagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insights_view_pager, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        View view = getView();
        viewPager = view.findViewById(R.id.insights_viewpager);
        insightsPagerAdapter = new InsightsViewPagerAdapter(this);
        viewPager.setAdapter(insightsPagerAdapter);
    }
}