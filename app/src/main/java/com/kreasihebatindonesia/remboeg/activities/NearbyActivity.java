package com.kreasihebatindonesia.remboeg.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.kreasihebatindonesia.remboeg.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class NearbyActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    SupportMapFragment mMapView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        ButterKnife.bind(this);

        mMapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mMapView);
        mMapView.getMapAsync(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
