package com.kreasihebatindonesia.remboeg.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.activities.NearbyActivity;
import com.kreasihebatindonesia.remboeg.adapters.NearbyAdapter;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.interfaces.INearby;
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

import static com.google.android.gms.internal.zzagz.runOnUiThread;

/**
 * Created by InfinityLogic on 11/8/2017.
 */

public class NearbyFragmentEvent extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private NearbyAdapter mNearbyAdapter;
    private int selectedPosition = -1;

    SupportMapFragment mMapView;
    GoogleMap map;

    private GPSTracker gps;
    private List<NearbyModel> mNearbys = new ArrayList<>();
    private List<Marker> mMarkers = new ArrayList<>();

    public static NearbyFragmentEvent newInstance(int index) {
        NearbyFragmentEvent fragment = new NearbyFragmentEvent();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_event, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gps = new GPSTracker(getContext());
        gps.getLocation();

        mMapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mMapView);
        mMapView.getMapAsync(this);

        mNearbyAdapter = new NearbyAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mNearbyAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper() {
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
        mRecyclerView.addItemDecoration(new VerticalOffsetDecoration(getActivity()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(false);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }else{
            map.setMyLocationEnabled(true);
        }

        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);

        getNearby(Const.DUMMY_LOCATION_ID, gps.getLocation().getLatitude(),gps.getLocation().getLongitude(), 25 );
    }

    private void onViewSnapped(int index) {

        final NearbyModel dModel = mNearbyAdapter.getItem(index);
        LatLng latlong = new LatLng(dModel.getLatLocation(),dModel.getLngLocation());
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            public View getInfoWindow(Marker arg0)
            {
                View v = getActivity().getLayoutInflater().inflate(R.layout.marker_map_window, null);
                TextView tView = (TextView)v.findViewById(R.id.txtTitle);
                tView.setText(dModel.getTitle());
                return v;
            }
            public View getInfoContents(Marker arg0)
            {
                return null;
            }
        });

        mMarkers.get(index).showInfoWindow();

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
                .url(Const.METHOD_NEARBY_EVENT)
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
                            mNearby.setImage(Utils.optString(resultObject, "image"));
                            mNearby.setTicket(Utils.optString(resultObject, "ticket"));

                            mNearbys.add(mNearby);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initializeMarker();
                                mNearbyAdapter.setItems(mNearbys);
                                INearby mNearby = (NearbyActivity) getActivity();
                                mNearby.onCount(10);

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

        LatLng latlong = new LatLng(mNearbys.get(0).getLatLocation(),mNearbys.get(0).getLngLocation());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlong).zoom(15).build();
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
        {
            public View getInfoWindow(Marker arg0)
            {
                View v = getActivity().getLayoutInflater().inflate(R.layout.marker_map_window, null);
                TextView tView = (TextView)v.findViewById(R.id.txtTitle);
                tView.setText(mNearbys.get(0).getTitle());
                return v;
            }
            public View getInfoContents(Marker arg0)
            {
                return null;
            }
        });

        mMarkers.get(0).showInfoWindow();

    }

    private void createMarker(double lat, double lng, final String title, String snippet) {

        Marker marker = map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));

        mMarkers.add(marker);

    }
}
