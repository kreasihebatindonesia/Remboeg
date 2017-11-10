package com.kreasihebatindonesia.remboeg.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.activities.SearchActivity;
import com.kreasihebatindonesia.remboeg.activities.SearchNearbyActivity;
import com.kreasihebatindonesia.remboeg.adapters.SearchAdapter;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.interfaces.ISearch;
import com.kreasihebatindonesia.remboeg.models.SearchModel;
import com.kreasihebatindonesia.remboeg.utils.Utils;
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
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.google.android.gms.internal.zzagz.runOnUiThread;

/**
 * Created by InfinityLogic on 11/10/2017.
 */

public class SearchFragmentEvent extends Fragment {
    @BindView(R.id.mLoader)
    MKLoader mLoader;
    @BindView(R.id.mListView)
    ListView mListView;

    private SearchAdapter mAdapter;

    public static SearchFragmentEvent newInstance(int index) {
        SearchFragmentEvent fragment = new SearchFragmentEvent();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_event, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    public void getSearch(String search){
        final ArrayList<SearchModel> mSearchModelList = new ArrayList<>();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("search", search + "")
                .build();

        Request request = new Request.Builder()
                .url(Const.METHOD_SEARCH)
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
                    mSearchModelList.clear();


                    JSONObject jsonObj = new JSONObject(response.body().string());
                    boolean mError = jsonObj.getInt("status") == 0?true:false;
                    if(!mError) {
                        final JSONArray jsonArray = new JSONArray(jsonObj.getString("result"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject resultObject = jsonArray.getJSONObject(i);
                            SearchModel sModel = new SearchModel();
                            sModel.setId(Utils.optInt(resultObject, "id"));
                            sModel.setIdType(Utils.optInt(resultObject, "id_type"));
                            sModel.setTitle(Utils.optString(resultObject, "title"));
                            sModel.setImage(Utils.optString(resultObject, "image"));
                            sModel.setTicket(Utils.optString(resultObject, "ticket"));
                            sModel.setAddress(Utils.optString(resultObject, "address"));

                            mSearchModelList.add(sModel);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mLoader.setVisibility(View.GONE);
                                mListView.setVisibility(View.VISIBLE);

                                mAdapter = new SearchAdapter(getActivity(), mSearchModelList);
                                mListView.setAdapter(mAdapter);


                                ISearch mNearby = (SearchActivity) getActivity();
                                mNearby.onCount(jsonArray.length(), getArguments().getInt("index"));

                            }
                        });

                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mLoader.setVisibility(View.GONE);
                                mListView.setVisibility(View.VISIBLE);

                                if (mAdapter != null)
                                    mAdapter.notifyDataSetChanged();

                                mListView.setAdapter(null);
                                ISearch mNearby = (SearchActivity) getActivity();
                                mNearby.onCount(0, getArguments().getInt("index"));
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
