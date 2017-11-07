package com.kreasihebatindonesia.remboeg.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.services.GPSTracker;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class DetailMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    @BindView(R.id.btnDirection)
    Button btnDirection;
    @BindView(R.id.txtVenue)
    TextView txtVenue;
    @BindView(R.id.txtAddress)
    TextView txtAddress;

    SupportMapFragment mMapView;
    GoogleMap map;
    Marker destinationMarker;

    LatLng mCurrentLatLng;
    String mTitle;
    String mAddress;
    String mVenue;

    GPSTracker gps;
    LatLng origin, destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);

        ButterKnife.bind(this);
        mMapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mMapView);
        mMapView.getMapAsync(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b != null) {
            mTitle = b.getString("title");
            mAddress = b.getString("address");
            mVenue = b.getString("venue");

            mCurrentLatLng = new LatLng(b.getDouble("lat"), b.getDouble("lng"));
        }

        txtVenue.setText(mVenue);
        txtAddress.setText(mAddress);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);

        gps = new GPSTracker(getBaseContext());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                gps.getLocation();
                map.setMyLocationEnabled(true);
            }
        }
        else {
            gps.getLocation();
            map.setMyLocationEnabled(true);
        }

        destination = new LatLng(mCurrentLatLng.latitude, mCurrentLatLng.longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(destination).zoom(17).build();
        Marker marker = map.addMarker(new MarkerOptions().position(destination).title(mTitle));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            public View getInfoWindow(Marker arg0)
            {
                View v = getLayoutInflater().inflate(R.layout.marker_map_window, null);
                TextView tView = (TextView)v.findViewById(R.id.txtTitle);
                tView.setText(mTitle);
                return v;
            }
            public View getInfoContents(Marker arg0)
            {
                return null;
            }
        });

        btnDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                origin = new LatLng(gps.getLocation().getLatitude(), gps.getLocation().getLongitude());
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", destination.latitude, destination.longitude, "Where the party is at");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

            }
        });

        marker.showInfoWindow();
    }

}