package com.kreasihebatindonesia.remboeg.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.app.BaseApplication;
import com.kreasihebatindonesia.remboeg.networks.ConnectivityReceiver;
import com.kreasihebatindonesia.remboeg.pagers.MainViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    @BindView(R.id.mViewPager)
    AHBottomNavigationViewPager mViewPager;
    @BindView(R.id.mBottomNav)
    AHBottomNavigation mBottomNav;

    private Fragment mCurrentFragment;
    private MainViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        InitNavBottom();

        ConnectivityReceiver.isConnected();
    }

    void InitNavBottom(){

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.bottom_tab_1, R.drawable.ic_home,  R.color.colorNavigation);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.bottom_tab_2, R.drawable.ic_pin, R.color.colorNavigation);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.bottom_tab_3, R.drawable.ic_map_marker, R.color.colorNavigation);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.bottom_tab_4, R.drawable.ic_account, R.color.colorNavigation);


        mBottomNav.addItem(item1);
        mBottomNav.addItem(item2);
        mBottomNav.addItem(item3);
        mBottomNav.addItem(item4);

        mBottomNav.setForceTint(true);
        mBottomNav.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        mBottomNav.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (mCurrentFragment == null) {
                    mCurrentFragment = mViewPagerAdapter.getCurrentFragment();
                }

                mViewPager.setCurrentItem(position, false);

                if (mCurrentFragment == null) {
                    return true;
                }

                mCurrentFragment = mViewPagerAdapter.getCurrentFragment();

                return true;
            }
        });

        mViewPager.setOffscreenPageLimit(4);
        mViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mCurrentFragment = mViewPagerAdapter.getCurrentFragment();


    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if(!isConnected)
            Toast.makeText(this, "Jaringan tidak tersedia. Mohon ulangi lagi.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        BaseApplication.getInstance().setConnectivityListener(this);
    }

}
