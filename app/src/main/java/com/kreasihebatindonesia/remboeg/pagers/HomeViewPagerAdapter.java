package com.kreasihebatindonesia.remboeg.pagers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kreasihebatindonesia.remboeg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private Context mContext;

    public HomeViewPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
        //return mFragmentTitleList.get(position);
    }
    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab_main, null);
        TextView tv = (TextView) v.findViewById(R.id.txtTabTitle);
        tv.setText(mFragmentTitleList.get(position));
        return v;
    }
}
