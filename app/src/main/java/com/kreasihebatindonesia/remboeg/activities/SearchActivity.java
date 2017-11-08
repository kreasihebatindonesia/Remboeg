package com.kreasihebatindonesia.remboeg.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.adapters.SearchAdapter;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.models.DbHistoryModel;
import com.kreasihebatindonesia.remboeg.models.SearchModel;
import com.kreasihebatindonesia.remboeg.utils.SqliteDatabaseHandler;
import com.kreasihebatindonesia.remboeg.utils.Utils;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
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

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtSearch)
    TextView txtSearch;
    @BindView(R.id.txtClearHistory)
    TextView txtClearHistory;

    @BindView(R.id.mListView)
    ListView mListView;
    @BindView(R.id.mListviewHistory)
    ListView mListviewHistory;
    @BindView(R.id.mLoader)
    MKLoader mLoader;

    @BindView(R.id.mExpandSearch)
    LinearLayout mExpandSearch;
    @BindView(R.id.mResultSearch)
    LinearLayout mResultSearch;

    @BindView(R.id.mCardView)
    CardView mCardView;


    private SearchAdapter mAdapter;
    private SqliteDatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        db = new SqliteDatabaseHandler(getApplicationContext());


        TextWatcher filterTextWatcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before,int count) {
                if(count <= 0)
                {
                    mResultSearch.setVisibility(View.GONE);
                    mExpandSearch.setVisibility(View.VISIBLE);
                }else{
                    //getSearch(s.toString());
                    //mResultSearch.setVisibility(View.VISIBLE);
                    //mExpandSearch.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        txtSearch.addTextChangedListener(filterTextWatcher);

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    getSearch(txtSearch.getText().toString());

                    if(txtSearch.getText().toString().length() > 0){
                        DbHistoryModel history = new DbHistoryModel();
                        history.setTitle(txtSearch.getText().toString());
                        db.createHistorySearch(history);
                    }

                }
                return false;
            }
        });

        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNearby = new Intent(getBaseContext(), NearbyActivity.class);
                startActivity(iNearby);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchModel entry = (SearchModel) parent.getAdapter().getItem(position);
                Intent i = new Intent(SearchActivity.this, DetailEventActivity.class);
                i.putExtra("id_event", entry.getId());
                startActivity(i);
                //finish();
            }
        });

        mListviewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String entry = (String) parent.getAdapter().getItem(position);
                txtSearch.setText(entry);
                getSearch(entry);
            }
        });

        txtClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });

        getHistorySearch();

        mResultSearch.setVisibility(View.GONE);
        mExpandSearch.setVisibility(View.VISIBLE);

        db.closeDB();
    }

    void getHistorySearch(){
        List<DbHistoryModel> dbHistory = db.getAllHistorySearch();
        ArrayList<String> values = new ArrayList<>();
        for (DbHistoryModel history : dbHistory) {
            values.add(history.getTitle());
        }

        if(values.size() > 0)
            mListviewHistory.setVisibility(View.VISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };
        mListviewHistory.setAdapter(adapter);
    }

    void clearHistory(){
        db.clearHistory();
        mListviewHistory.setVisibility(View.VISIBLE);
        getHistorySearch();
    }

    void getSearch(String search){
        mResultSearch.setVisibility(View.VISIBLE);
        mExpandSearch.setVisibility(View.GONE);

        mLoader.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);

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
                mResultSearch.setVisibility(View.GONE);
                mExpandSearch.setVisibility(View.VISIBLE);
                mLoader.setVisibility(View.VISIBLE);
                mListView.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    mSearchModelList.clear();
                    JSONObject jsonObj = new JSONObject(response.body().string());
                    boolean mError = jsonObj.getInt("status") == 0?true:false;
                    if(!mError) {
                        JSONArray jsonArray = new JSONArray(jsonObj.getString("result"));
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


                                mResultSearch.setVisibility(View.VISIBLE);
                                mExpandSearch.setVisibility(View.GONE);

                                mLoader.setVisibility(View.GONE);
                                mListView.setVisibility(View.VISIBLE);

                                mAdapter = new SearchAdapter(SearchActivity.this, mSearchModelList);
                                mListView.setAdapter(mAdapter);

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