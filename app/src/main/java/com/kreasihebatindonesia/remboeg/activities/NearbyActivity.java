package com.kreasihebatindonesia.remboeg.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.adapters.NearbyAdapter;
import com.kreasihebatindonesia.remboeg.fragments.HomeFragmentEvent;
import com.kreasihebatindonesia.remboeg.fragments.HomeFragmentJob;
import com.kreasihebatindonesia.remboeg.fragments.NearbyFragmentEvent;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.interfaces.INearby;
import com.kreasihebatindonesia.remboeg.models.NearbyModel;
import com.kreasihebatindonesia.remboeg.pagers.HomeViewPagerAdapter;
import com.kreasihebatindonesia.remboeg.pagers.NearbyViewPagerAdapter;
import com.kreasihebatindonesia.remboeg.services.GPSTracker;
import com.kreasihebatindonesia.remboeg.utils.Utils;
import com.kreasihebatindonesia.remboeg.utils.VerticalOffsetDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class NearbyActivity extends AppCompatActivity implements INearby {
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

        /*
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }
           */

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new NearbyViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFrag(NearbyFragmentEvent.newInstance(0), "Event & Workshop");
        adapter.addFrag(NearbyFragmentEvent.newInstance(0), "Job Fairs");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

    }

    @Override
    public void onCount(int count) {
        adapter.setCount(count);
        //adapter.notifyDataSetChanged();
    }
}
