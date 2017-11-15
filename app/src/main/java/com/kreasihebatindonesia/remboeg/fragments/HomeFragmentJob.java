package com.kreasihebatindonesia.remboeg.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.adapters.EventAdapter;
import com.kreasihebatindonesia.remboeg.adapters.JobAdapter;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.interfaces.ILocation;
import com.kreasihebatindonesia.remboeg.models.EventModel;
import com.kreasihebatindonesia.remboeg.models.JobModel;
import com.kreasihebatindonesia.remboeg.utils.Utils;

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
 * Created by IT DCM on 06/11/2017.
 */

public class HomeFragmentJob extends Fragment implements ILocation {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    RecyclerView.LayoutManager mLayoutManager;
    private JobAdapter mJobAdapter;

    public static HomeFragmentJob newInstance(int index, int idLocation) {
        HomeFragmentJob fragment = new HomeFragmentJob();
        Bundle b = new Bundle();
        b.putInt("index", index);
        b.putInt("id_location", idLocation);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_job, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getFromServer(getArguments().getInt("id_location"));

        mLayoutManager = new GridLayoutManager(getContext(), 1);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFromServer(getArguments().getInt("id_location"));
            }
        });

    }

    @Override
    public void getDataByLocationID(int idLocation) {
        getFromServer(idLocation);
    }

    @Override
    public void onLocationFinish() {

    }

    public void getFromServer(int id_location){
        final List<JobModel> mJobModelList = new ArrayList<>();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id_location", id_location + "")
                .build();

        Request request = new Request.Builder()
                .url(Const.METHOD_JOB_ACTIVE)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {

                    JSONObject jsonObj = new JSONObject(response.body().string());
                    boolean mError = jsonObj.getInt("status") == 0?true:false;
                    if(!mError){
                        JSONArray jsonArray = new JSONArray(jsonObj.getString("result"));

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject resultObject = jsonArray.getJSONObject(i);
                            JobModel mJobModel = new JobModel();
                            mJobModel.setIdJob(Utils.optInt(resultObject, "id"));
                            mJobModel.setTitleJob(Utils.optString(resultObject, "title"));
                            mJobModel.setImageJob(Utils.optString(resultObject, "image"));
                            mJobModel.setEndDateJob(Utils.optString(resultObject, "end_date"));
                            mJobModel.setSalaryJob(Utils.optString(resultObject, "salary"));
                            mJobModel.setTotalLikes(Utils.optInt(resultObject, "likes"));
                            mJobModel.setTotalShares(Utils.optInt(resultObject, "shares"));
                            mJobModel.setTotalViews(Utils.optInt(resultObject, "views"));

                            mJobModelList.add(mJobModel);
                        }


                        mJobAdapter = new JobAdapter(getContext(), mJobModelList);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ILocation iHomeFragment = (HomeFragment) getFragmentManager().getFragments().get(0).getParentFragment();
                                iHomeFragment.onLocationFinish();

                                mRecyclerView.invalidate();
                                mRecyclerView.invalidateItemDecorations();
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                                mRecyclerView.setAdapter(mJobAdapter);

                            }
                        });



                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerView.invalidate();
                                mRecyclerView.invalidateItemDecorations();
                                mRecyclerView.removeAllViewsInLayout();
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (JSONException e) {
                    Log.d("ERROR", e.toString());
                }
            }
        });

    }
}
