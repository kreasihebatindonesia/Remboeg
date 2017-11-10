package com.kreasihebatindonesia.remboeg.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.fragments.NearbyFragmentEvent;
import com.kreasihebatindonesia.remboeg.fragments.NearbyFragmentJob;
import com.kreasihebatindonesia.remboeg.interfaces.ISearch;
import com.kreasihebatindonesia.remboeg.pagers.NearbyViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class SearchNearbyActivity extends AppCompatActivity implements ISearch {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    private NearbyViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        ButterKnife.bind(this);

        mToolbar.setTitle("Near Me");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);


        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));

        }

        mTabLayout.setScrollPosition(0,0f,true);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
                TextView t = (TextView)tab.getCustomView().findViewById(R.id.txtTabTitle);
                TextView tCount = (TextView)tab.getCustomView().findViewById(R.id.txtTabCount);
                t.setTextColor(tabIconColor);
                tCount.setBackgroundResource(R.drawable.rounded_50dp_primary_color);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorBlack_30);
                TextView t = (TextView)tab.getCustomView().findViewById(R.id.txtTabTitle);
                TextView tCount = (TextView)tab.getCustomView().findViewById(R.id.txtTabCount);
                t.setTextColor(tabIconColor);
                tCount.setBackgroundResource(R.drawable.rounded_50dp_black_30_percent);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
                TextView t = (TextView)tab.getCustomView().findViewById(R.id.txtTabTitle);
                TextView tCount = (TextView)tab.getCustomView().findViewById(R.id.txtTabCount);
                t.setTextColor(tabIconColor);
                tCount.setBackgroundResource(R.drawable.rounded_50dp_primary_color);

            }
        });


        mTabLayout.getTabAt(0).select();
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new NearbyViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFrag(NearbyFragmentEvent.newInstance(0), getString(R.string.header_tab_1));
        adapter.addFrag(NearbyFragmentJob.newInstance(1), getString(R.string.header_tab_2));
        adapter.addFrag(NearbyFragmentEvent.newInstance(2), getString(R.string.header_tab_3));
        adapter.addFrag(NearbyFragmentEvent.newInstance(3), getString(R.string.header_tab_4));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onCount(int count, int position) {
        TextView tCount = (TextView)mTabLayout.getTabAt(position).getCustomView().findViewById(R.id.txtTabCount);
        tCount.setText(Integer.toString(count));
    }
}
