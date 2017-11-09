package com.kreasihebatindonesia.remboeg.pagers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kreasihebatindonesia.remboeg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by InfinityLogic on 11/8/2017.
 */

public class NearbyViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Integer> mFragmentCountList = new ArrayList<>();

    private int[] imageResId = {
            R.drawable.ic_home_light,
            R.drawable.ic_home_light,
            R.drawable.ic_home_light };
    private Context mContext;

    public NearbyViewPagerAdapter(FragmentManager manager, Context context) {
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

    public void setCount(int count){
        mFragmentCountList.add(count);
        mFragmentCountList.add(count);
        mFragmentCountList.add(count);
        //notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return mFragmentTitleList.get(position);

        /*
        int hasilAkhir;
        //Drawable image = mContext.getResources().getDrawable(imageResId[position]);
        //Drawable wrapped = DrawableCompat.wrap(image);
        //DrawableCompat.setTint(wrapped, mContext.getResources().getColor(R.color.colorAccent));
        //wrapped.setBounds(0, 0, wrapped.getIntrinsicWidth(), wrapped.getIntrinsicHeight());
        // Replace blank spaces with image icon
        if(mFragmentCountList.size() <= 0 )
            hasilAkhir = 0;
        else
            hasilAkhir = mFragmentCountList.get(position);

        SpannableString sb = new SpannableString(mFragmentTitleList.get(position) + " [ " + hasilAkhir +  " ]  " );
        //ImageSpan imageSpan = new ImageSpan(wrapped, ImageSpan.ALIGN_BOTTOM);

        //sb.setSpan("[4]", 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //return sb;

           */
        return null;
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.txtTabTitle);
        tv.setText(mFragmentTitleList.get(position));
        ImageView img = (ImageView) v.findViewById(R.id.imgTab);
        img.setImageResource(imageResId[position]);
        return v;
    }


}