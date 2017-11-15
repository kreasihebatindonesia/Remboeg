package com.kreasihebatindonesia.remboeg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.app.BaseApplication;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.networks.ConnectivityReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IT DCM on 15/11/2017.
 */

public class DetailJobActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, OnMapReadyCallback {

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    SupportMapFragment mMapView;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        ButterKnife.bind(this);
        //mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        mMapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mMapView);
        mMapView.getMapAsync(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b != null) {
            //getJobDetail(Const.DUMMY_USER_ID, b.getInt("id_job"));
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ConnectivityReceiver.isConnected();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

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
