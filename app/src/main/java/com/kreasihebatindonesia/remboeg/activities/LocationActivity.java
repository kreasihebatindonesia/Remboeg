package com.kreasihebatindonesia.remboeg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.adapters.LocationAdapter;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.models.LocationModel;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class LocationActivity extends AppCompatActivity {

    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtSearch)
    EditText txtSearch;

    MKLoader mLoader;

    private ArrayList<LocationModel> mLocationModelList;
    private LocationAdapter mAdapter;
    private LocationModel mCurrentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ButterKnife.bind(this);

        mLocationModelList = new ArrayList<>();

        Gson gson = new Gson();
        Intent i= getIntent();
        Bundle b = i.getExtras();

        mCurrentLocation = gson.fromJson(b.getString("mCurrentLocation"), LocationModel.class);
        mLocationModelList = GetDataLocation();

        mAdapter = new LocationAdapter(LocationActivity.this, mCurrentLocation, mLocationModelList);
        mListView.setAdapter(mAdapter);

        LayoutInflater myinflater = getLayoutInflater();
        ViewGroup myHeader = (ViewGroup)myinflater.inflate(R.layout.listview_header_location, mListView, false);
        mLoader = (MKLoader) myHeader.findViewById(R.id.mLoader);

        mListView.addHeaderView(myHeader, null, false);



        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextWatcher filterTextWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before,int count) {
                mAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        txtSearch.addTextChangedListener(filterTextWatcher);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationModel entry = (LocationModel) parent.getAdapter().getItem(position);
                Intent intent = new Intent();
                Gson gson = new Gson();
                intent.putExtra("mCurrentLocation", gson.toJson(entry));
                setResult(RESULT_OK, intent);
                mAdapter.notifyDataSetChanged();
                finish();
            }
        });
    }

    public ArrayList<LocationModel> GetDataLocation(){

        Request request = new Request.Builder()
                .url(Const.METHOD_CITY)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObj = new JSONObject(response.body().string());
                    boolean mError = jsonObj.getInt("status") == 0?true:false;
                    if(!mError){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONArray jsonArray = new JSONArray(jsonObj.getString("result"));

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject resultObject = jsonArray.getJSONObject(i);
                                        LocationModel mLocationModel = new LocationModel(resultObject.getInt("id"), resultObject.getString("location"));
                                        mLocationModelList.add(mLocationModel);
                                    }
                                    mLoader.setVisibility(View.GONE);
                                    mAdapter = new LocationAdapter(LocationActivity.this, mCurrentLocation, mLocationModelList);
                                    mListView.setAdapter(mAdapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    Log.d("ERROR", e.toString());
                }
            }
        });

        return mLocationModelList;
    }
}