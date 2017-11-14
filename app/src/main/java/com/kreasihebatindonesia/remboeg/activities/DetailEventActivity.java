package com.kreasihebatindonesia.remboeg.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.app.BaseApplication;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.models.EventModel;
import com.kreasihebatindonesia.remboeg.networks.ConnectivityReceiver;
import com.kreasihebatindonesia.remboeg.utils.Utils;

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

public class DetailEventActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, OnMapReadyCallback {

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mImageEvent)
    ImageView mImageEvent;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtDesc)
    TextView txtDesc;
    @BindView(R.id.txtVenue)
    TextView txtVenue;
    @BindView(R.id.txtAddress)
    TextView txtAddress;
    @BindView(R.id.txtDateStart)
    TextView txtDateStart;
    @BindView(R.id.txtDateEnd)
    TextView txtDateEnd;
    @BindView(R.id.txtTimeStart)
    TextView txtTimeStart;
    @BindView(R.id.txtTimeEnd)
    TextView txtTimeEnd;
    @BindView(R.id.txtEmail)
    TextView txtEmail;
    @BindView(R.id.txtWebsite)
    TextView txtWebsite;
    @BindView(R.id.txtPhone)
    TextView txtPhone;
    @BindView(R.id.txtTicket)
    TextView txtTicket;

    @BindView(R.id.txtContactInfo)
    TextView txtContactInfo;
    @BindView(R.id.txtTicketInfo)
    TextView txtTicketInfo;

    @BindView(R.id.mContentEmail)
    LinearLayout mContentEmail;
    @BindView(R.id.mContentWebsite)
    LinearLayout mContentWebsite;
    @BindView(R.id.mContentPhone)
    LinearLayout mContentPhone;
    @BindView(R.id.mTicket)
    LinearLayout mTicket;
    @BindView(R.id.mDateStart)
    LinearLayout mDateStart;
    @BindView(R.id.mDateEnd)
    LinearLayout mDateEnd;
    @BindView(R.id.mMapLocation)
    LinearLayout mMapLocation;

    @BindView(R.id.btnPin)
    Button btnPin;
    @BindView(R.id.btnLike)
    Button btnLike;
    @BindView(R.id.btnCall)
    Button btnCall;
    @BindView(R.id.btnShare)
    Button btnShare;

    SupportMapFragment mMapView;
    GoogleMap map;

    private List<String> mListCall = new ArrayList<>();

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
            getEventDetail(Const.DUMMY_USER_ID, b.getInt("id_event"));
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ConnectivityReceiver.isConnected();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setScrollGesturesEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);
    }

    void getEventDetail(int id_user, int id_event) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_user", id_user + "")
                .addFormDataPart("id_event", id_event + "")
                .build();

        Request request = new Request.Builder()
                .url(Const.METHOD_EVENT_DETAIL_ACTIVE)
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
                        JSONObject jsonResult = jsonObj.getJSONObject("result");
                        final EventModel mEvent = new EventModel();
                        mEvent.setIdEvent(Utils.optInt(jsonResult, "id"));
                        mEvent.setCategoryEvent(Utils.optString(jsonResult, "category"));
                        mEvent.setLocationEvent(Utils.optString(jsonResult, "location"));
                        mEvent.setTitleEvent(Utils.optString(jsonResult, "title"));
                        mEvent.setDescEvent(Utils.optString(jsonResult, "description"));
                        mEvent.setImageEvent(Utils.optString(jsonResult, "image"));
                        mEvent.setVenueEvent(Utils.optString(jsonResult, "venue"));
                        mEvent.setAddressEvent(Utils.optString(jsonResult, "address"));
                        mEvent.setLocationEvent(Utils.optDouble(jsonResult, "lat_loc"), Utils.optDouble(jsonResult, "lng_loc"));
                        mEvent.setDateStartEvent(Utils.optString(jsonResult, "start_date"), true);
                        mEvent.setDateEndEvent(Utils.optString(jsonResult, "end_date"), true);
                        mEvent.setTimeStartEvent(Utils.optString(jsonResult, "start_time"));
                        mEvent.setTimeEndEvent(Utils.optString(jsonResult, "end_time"));
                        mEvent.setContactEvent(Utils.optString(jsonResult, "contact"));
                        mEvent.setEmailEvent(Utils.optString(jsonResult, "email"));
                        mEvent.setWebsiteEvent(Utils.optString(jsonResult, "website"));
                        mEvent.setTicketEvent(Utils.optString(jsonResult, "ticket"));
                        mEvent.setTotalLikes(Utils.optInt(jsonResult, "likes"));
                        mEvent.setTotalViews(Utils.optInt(jsonResult, "views"));
                        mEvent.setTotalShares(Utils.optInt(jsonResult, "shares"));
                        mEvent.setLikeEvent(jsonResult.getBoolean("already_like"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mToolbar.setTitle(mEvent.getTitleEvent());
                                Glide.with(getApplicationContext()).load(Const.URL_UPLOADS + mEvent.getImageEvent()).into(mImageEvent);

                                txtTitle.setVisibility(mEvent.getTitleEvent() == null? View.GONE: View.VISIBLE);
                                txtDesc.setVisibility(mEvent.getDescEvent() == null? View.GONE: View.VISIBLE);
                                txtTitle.setText(mEvent.getTitleEvent());
                                txtDesc.setText(mEvent.getDescEvent());
                                txtVenue.setText(mEvent.getVenueEvent());
                                txtAddress.setText(mEvent.getAddressEvent());

                                mDateStart.setVisibility(mEvent.getDateStartEvent() == null? View.GONE: View.VISIBLE);
                                mDateEnd.setVisibility(mEvent.getDateEndEvent() == null? View.GONE: View.VISIBLE);
                                txtDateStart.setText(mEvent.getDateStartEvent());
                                txtDateEnd.setText(mEvent.getDateEndEvent());
                                txtTimeStart.setText(mEvent.getTimeStartEvent());
                                txtTimeEnd.setText(mEvent.getTimeEndEvent());

                                mMapLocation.setVisibility(mEvent.getLocationLatEvent() == 0? View.GONE: View.VISIBLE);
                                getLocationMap(mEvent.getLocationLatEvent(), mEvent.getLocationLngEvent(), mEvent.getVenueEvent(), mEvent.getAddressEvent(), mEvent.getTitleEvent());

                                mContentEmail.setVisibility(mEvent.getEmailEvent() == null? View.GONE: View.VISIBLE);
                                mContentWebsite.setVisibility(mEvent.getWebsiteEvent() == null? View.GONE: View.VISIBLE);
                                mContentPhone.setVisibility(mEvent.getContactEvent() == null? View.GONE: View.VISIBLE);

                                if(mEvent.getEmailEvent() != null){
                                    mListCall.add("Email");
                                }
                                if(mEvent.getContactEvent() != null){
                                    mListCall.add("Telepon");
                                    mListCall.add("SMS");
                                }


                                if(mEvent.getEmailEvent() == null && mEvent.getWebsiteEvent() == null && mEvent.getContactEvent() == null){
                                    txtContactInfo.setVisibility(View.VISIBLE);
                                }

                                txtEmail.setText(mEvent.getEmailEvent());
                                txtWebsite.setText(mEvent.getWebsiteEvent());
                                txtPhone.setText(mEvent.getContactEvent());

                                txtTicket.setText(mEvent.getTicketEvent());
                                mTicket.setVisibility(mEvent.getTicketEvent() == null? View.GONE: View.VISIBLE);
                                if(mEvent.getTicketEvent() == null){
                                    txtTicketInfo.setVisibility(View.VISIBLE);
                                }

                                if (mEvent.getLikeEvent()) {
                                    btnLike.setBackgroundResource(R.drawable.button_rounded_50dp_primary_color);
                                    btnLike.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorWhite));
                                    btnLike.setText("TIDAK SUKA");
                                }


                                btnShare.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent sendIntent = new Intent();
                                        sendIntent.setAction(Intent.ACTION_SEND);
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, Const.HOST_ADDRESS + "/" + mEvent.getTicketEvent() + "/" + mEvent.getIdEvent());
                                        sendIntent.setType("text/plain");
                                        startActivity(sendIntent);
                                    }
                                });

                                btnCall.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (mListCall.size() > 0)
                                            showCallDialog();
                                        else
                                            Snackbar.make(btnCall, "Informasi tidak tersedia.", Snackbar.LENGTH_SHORT).show();
                                    }
                                });


                                setViewCountEvent(mEvent.getIdEvent());



                            }
                        });

                    }
                } catch (JSONException e) {
                    Log.d("ERROR", e.toString());
                }


            }
        });
    }

    void HideView(){
        mTicket.setVisibility(View.GONE);
    }

    void setViewCountEvent(int id_event) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_event", id_event + "")
                .build();

        Request request = new Request.Builder()
                .url(Const.METHOD_EVENT_SET_VIEW)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    void getLocationMap(final double lat, final double lng, final String venue, final String address, final String title) {
        LatLng latlong = new LatLng(lat, lng);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latlong).zoom(15).build();
        map.addMarker(new MarkerOptions().position(latlong).title("Lokasi"));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                            Intent iMapDetail = new Intent(getBaseContext(), DetailMapActivity.class);
                            iMapDetail.putExtra("title", title);
                            iMapDetail.putExtra("venue", venue);
                            iMapDetail.putExtra("address", address);
                            iMapDetail.putExtra("lat", lat);
                            iMapDetail.putExtra("lng", lng);
                            startActivity(iMapDetail);

            }
        });

    }

    void showCallDialog() {
        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setItems(mListCall.toArray(new String[0]),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                switch (mListCall.get(i)) {
                                    case "Email":
                                        sendEmail();
                                        break;
                                    case "Telepon":
                                        callNumber();
                                        break;
                                    case "Sms":
                                        sendSMSMessage();
                                        break;
                                }
                            }
                        })
                .show();
    }

    private void sendSMSMessage() {
        try {
            Uri uri = Uri.parse("smsto:" + txtPhone.getText());
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
            smsIntent.putExtra("sms_body", txtTitle.getText());
            startActivity(smsIntent);
        } catch (Exception e) {
            Toast.makeText(this, "SMS gagal, mohon coba kembali!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void callNumber() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + txtPhone.getText()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            return;
        }
        startActivity(callIntent);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
            Toast.makeText(getBaseContext(), "Aktifkan hak akses", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    private void sendEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txtEmail.getText()));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, txtTitle.getText());
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(emailIntent, "Pilih judul"));
    }
}
