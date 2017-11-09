package com.kreasihebatindonesia.remboeg.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.activities.LocationActivity;
import com.kreasihebatindonesia.remboeg.activities.SearchActivity;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.interfaces.ILocation;
import com.kreasihebatindonesia.remboeg.models.LocationModel;
import com.kreasihebatindonesia.remboeg.pagers.HomeViewPagerAdapter;
import com.kreasihebatindonesia.remboeg.services.GPSTracker;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.internal.zzagz.runOnUiThread;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class HomeFragment extends Fragment implements ILocation{
    @BindView(R.id.mAppBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.mCollapseToolbar)
    CollapsingToolbarLayout mCollapseToolbar;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.txtSelectCity)
    TextView txtSelectCity;
    @BindView(R.id.txtSearch)
    EditText txtSearch;
    @BindView(R.id.mLoader)
    MKLoader mLoader;
    @BindView(R.id.lytLocation)
    LinearLayout lytLocation;

    private int DummyFastIndex = 0;
    private HomeViewPagerAdapter adapter;
    private GPSTracker gps;
    private LocationModel mCurrentLocation;

    private static int CODE_RESULT_LOCATION = 1;

    public static HomeFragment newInstance(int index) {
        HomeFragment fragment = new HomeFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, view);

        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        mToolbar.setTitle(R.string.app_name);

        gps = new GPSTracker(getContext());
        FindLocation();

        txtSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLocation = new Intent(getActivity(), LocationActivity.class);
                Gson gson = new Gson();
                iLocation.putExtra("mCurrentLocation", gson.toJson(mCurrentLocation));
                startActivityForResult(iLocation, CODE_RESULT_LOCATION);
            }
        });

        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSearch = new Intent(getActivity(), SearchActivity.class);
                startActivity(iSearch);
            }
        });

        return view;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(HomeFragmentEvent.newInstance(0, mCurrentLocation.GetIdCity()), getString(R.string.header_tab_1));
        adapter.addFrag(HomeFragmentJob.newInstance(0, mCurrentLocation.GetIdCity()), getString(R.string.header_tab_2));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_RESULT_LOCATION) {
            if (resultCode == RESULT_OK) {
                Gson gson = new Gson();
                mCurrentLocation = gson.fromJson(data.getExtras().getString("mCurrentLocation"), LocationModel.class);
                txtSelectCity.setText(mCurrentLocation.GetNameCity());

                ILocation iLocEvent = (HomeFragmentEvent) adapter.getItem(0);
                iLocEvent.getDataByLocationID(mCurrentLocation.GetIdCity());
            }
        }


    }

    void FindLocation(){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("alias_location", gps.getStateNameLocation() + "")
                .build();

        Request request = new Request.Builder()
                .url(Const.METHOD_CITY_GPS)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    JSONObject jsonObj = new JSONObject(response.body().string());
                    boolean mError = jsonObj.getInt("status") == 0?true:false;
                    if(!mError){
                        JSONObject jsonResult = jsonObj.getJSONObject("result");
                        mCurrentLocation = new LocationModel(jsonResult.getInt("id"), jsonResult.getString("location"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(getContext(), mCurrentLocation.GetNameCity(), Toast.LENGTH_LONG).show();

                                setupViewPager(mViewPager);
                                mTabLayout.setupWithViewPager(mViewPager);

                                lytLocation.setVisibility(View.VISIBLE);
                                txtSelectCity.setText(mCurrentLocation.GetNameCity());

                                Const.DUMMY_LOCATION_ID = mCurrentLocation.GetIdCity();
                            }
                        });

                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void getDataByLocationID(int idLocation) {

    }

    @Override
    public void onLocationFinish() {
        mLoader.setVisibility(View.GONE);
    }
}