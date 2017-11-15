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
import com.kreasihebatindonesia.remboeg.models.JobModel;
import com.kreasihebatindonesia.remboeg.networks.ConnectivityReceiver;
import com.kreasihebatindonesia.remboeg.utils.Utils;

import org.json.JSONArray;
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
        setContentView(R.layout.activity_detail_job);

        ButterKnife.bind(this);
        //mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        mMapView = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mMapView);
        mMapView.getMapAsync(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        if (b != null) {
            getJobDetail(Const.DUMMY_USER_ID, b.getInt("id_job"));
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

    void getJobDetail(int id_user, int id_job) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_user", id_user + "")
                .addFormDataPart("id_job", id_job + "")
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

                try {
                    JSONObject jsonObj = new JSONObject(response.body().string());
                    final boolean mError = jsonObj.getInt("status") == 0 ? true : false;
                    if (!mError) {
                        final JSONObject jsonResult = jsonObj.getJSONObject("result");
                        final JobModel mJob = new JobModel();
                        mJob.setIdJob(Utils.optInt(jsonResult, "id"));
                        mJob.setTitleJob(Utils.optString(jsonResult, "title"));
                        mJob.setDescEvent(Utils.optString(jsonResult, "description"));
                        mJob.setBenefitJob(Utils.optString(jsonResult, "benefit"));
                        mJob.setImageJob(Utils.optString(jsonResult, "image"));
                        mJob.setLocationJob(Utils.optDouble(jsonResult, "lat_loc"), Utils.optDouble(jsonResult, "lng_loc"));
                        mJob.setCompanyJob(Utils.optString(jsonResult, "company"));
                        mJob.setAddressJob(Utils.optString(jsonResult, "address"));
                        mJob.setSalaryJob(Utils.optString(jsonResult, "salary"));
                        mJob.setContactJob(Utils.optString(jsonResult, "contact"));
                        mJob.setEmailJob(Utils.optString(jsonResult, "email"));
                        mJob.setWebsiteJob(Utils.optString(jsonResult, "website"));
                        mJob.setEndDateJob(Utils.optString(jsonResult, "end_date"));
                        mJob.setTotalLikes(Utils.optInt(jsonResult, "likes"));
                        mJob.setTotalViews(Utils.optInt(jsonResult, "views"));
                        mJob.setTotalShares(Utils.optInt(jsonResult, "shares"));
                        mJob.setViaEmail(Utils.optBoolean(jsonResult, "via_email"));
                        mJob.setViaPost(Utils.optBoolean(jsonResult, "via_post"));
                        mJob.setViaOffice(Utils.optBoolean(jsonResult, "via_office"));
                        mJob.setAlreadyLikeJob(Utils.optBoolean(jsonResult, "already_like"));

                        JSONArray jsonRequired = jsonResult.getJSONArray("required");
                        for(int x=0; x < jsonRequired.length(); x++){
                            JSONObject jsonPosObject = jsonRequired.getJSONObject(x);
                            mJob.setPositionModels(Utils.optInt(jsonPosObject, ("id")), Utils.optString(jsonPosObject, ("required")), Utils.optString(jsonPosObject, ("description")));
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }
}
