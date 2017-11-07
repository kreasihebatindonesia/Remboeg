package com.kreasihebatindonesia.remboeg.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.adapters.DummyCardAdapter;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.models.DummyLocalInfoModel;
import com.kreasihebatindonesia.remboeg.models.EventModel;
import com.kreasihebatindonesia.remboeg.models.NearbyModel;
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

public class NearbyActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private DummyCardAdapter mDummyCardAdapter;
    private int selectedPosition = 0;

    SupportMapFragment mMapView;
    GoogleMap map;

    private GPSTracker gps;
    private List<NearbyModel> mNearbys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        ButterKnife.bind(this);

        gps = new GPSTracker(this);
        gps.getLocation();

        mMapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mMapView);
        mMapView.getMapAsync(this);

        mDummyCardAdapter = new DummyCardAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mDummyCardAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper(){
            @Override
            public View findSnapView(RecyclerView.LayoutManager layoutManager) {
                View view = super.findSnapView(layoutManager);

                if (view != null) {
                    final int newPosition = layoutManager.getPosition(view);

                    if (newPosition != selectedPosition && mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                        onViewSnapped(newPosition);
                        selectedPosition = newPosition;
                    }

                }

                return view;
            }
        };

        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setOnFlingListener(snapHelper);
        mRecyclerView.addItemDecoration(new VerticalOffsetDecoration(this));


        mToolbar.setTitle("Near Me");
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

        getNearby(Const.DUMMY_LOCATION_ID, gps.getLocation().getLatitude(),gps.getLocation().getLongitude(), 15 );
    }

    private void onViewSnapped(int index) {

        NearbyModel dModel = mDummyCardAdapter.getItem(index);
        LatLng latlong = new LatLng(dModel.getLatLocation(),dModel.getLngLocation());
        //CameraPosition cameraPosition = new CameraPosition.Builder().target(latlong).zoom(15).build();
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(latlong, 15);
        map.animateCamera(location);

    }

    public void getNearby(int id_location, double lat_loc, double lng_loc, double radius){
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_location", id_location + "")
                .addFormDataPart("lat_location", lat_loc + "")
                .addFormDataPart("lng_location", lng_loc + "")
                .addFormDataPart("radius", radius + "")
                .build();

        Request request = new Request.Builder()
                .url(Const.METHOD_NEARBY)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    JSONObject jsonObj = new JSONObject(response.body().string());
                    final boolean mError = jsonObj.getInt("status") == 0 ? true : false;
                    if (!mError) {
                        JSONArray jsonArray = new JSONArray(jsonObj.getString("result"));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject resultObject = jsonArray.getJSONObject(i);
                            final NearbyModel mNearby = new NearbyModel();
                            mNearby.setId(Utils.optInt(resultObject, "id"));
                            mNearby.setIdType(Utils.optInt(resultObject, "id_type"));
                            mNearby.setIdLocation(Utils.optInt(resultObject, "id_location"));
                            mNearby.setTitle(Utils.optString(resultObject, "title"));
                            mNearby.setLocation(Utils.optDouble(resultObject, "lat_loc"), Utils.optDouble(resultObject, "lng_loc"));
                            mNearby.setVenue(Utils.optString(resultObject, "venue"));
                            mNearby.setAddress(Utils.optString(resultObject, "address"));

                            mNearbys.add(mNearby);
                        }

                        /*
                        List<DummyLocalInfoModel> locations = new ArrayList<>();
                        locations.add(new DummyLocalInfoModel("Appvation Pty. Ltd. 1", "202/147 Pirie St, Adelaide"));
                        locations.add(new DummyLocalInfoModel("Appvation Pty. Ltd. 2 ", "202/147 Pirie St, Adelaide"));
                        locations.add(new DummyLocalInfoModel("Appvation Pty. Ltd. 3", "202/147 Pirie St, Adelaide"));
                        locations.add(new DummyLocalInfoModel("Appvation Pty. Ltd. 4", "202/147 Pirie St, Adelaide"));
                        */




                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initializeMarker();
                                mDummyCardAdapter.setItems(mNearbys);
                            }
                        });

                    }
                } catch (JSONException e) {
                    Log.d("ERROR", e.toString());
                }
            }
        });

    }

    void initializeMarker(){
        for(int i = 0 ; i < mNearbys.size() ; i++ ) {
            createMarker(mNearbys.get(i).getLatLocation(), mNearbys.get(i).getLngLocation(), mNearbys.get(i).getTitle(), mNearbys.get(i).getAddress());
        }
        LatLng latlong = new LatLng(gps.getLatitude(),gps.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlong).zoom(15).build();
        map.addMarker(new MarkerOptions().position(latlong).title("Lokasi"));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }

    protected void createMarker(double lat, double lng, final String title, String snippet) {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker)));

        marker.showInfoWindow();

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            public View getInfoWindow(Marker arg0)
            {
                View v = getLayoutInflater().inflate(R.layout.marker_map_window, null);
                TextView tView = (TextView)v.findViewById(R.id.txtTitle);
                tView.setText(title);
                return v;
            }
            public View getInfoContents(Marker arg0)
            {
                return null;
            }
        });
    }
}
