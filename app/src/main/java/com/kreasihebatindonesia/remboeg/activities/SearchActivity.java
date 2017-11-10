package com.kreasihebatindonesia.remboeg.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.adapters.SearchEventAdapter;
import com.kreasihebatindonesia.remboeg.fragments.SearchFragmentEvent;
import com.kreasihebatindonesia.remboeg.fragments.SearchFragmentJob;
import com.kreasihebatindonesia.remboeg.interfaces.ISearch;
import com.kreasihebatindonesia.remboeg.models.DbHistoryModel;
import com.kreasihebatindonesia.remboeg.pagers.SearchViewPagerAdapter;
import com.kreasihebatindonesia.remboeg.utils.SqliteDatabaseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class SearchActivity extends AppCompatActivity implements ISearch {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtSearch)
    TextView txtSearch;
    @BindView(R.id.txtClearHistory)
    TextView txtClearHistory;
    @BindView(R.id.mButtonClear)
    ImageButton mButtonClear;

    @BindView(R.id.mListviewHistory)
    ListView mListviewHistory;
    //@BindView(R.id.mLoader)
    //MKLoader mLoader;

    @BindView(R.id.mExpandSearch)
    LinearLayout mExpandSearch;
    @BindView(R.id.mResultSearch)
    LinearLayout mResultSearch;

    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    @BindView(R.id.mCardView)
    CardView mCardView;


    private SearchEventAdapter mAdapter;
    private SqliteDatabaseHandler db;
    private SearchViewPagerAdapter adapter;
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
                    mButtonClear.setVisibility(View.GONE);
                }else{
                    mButtonClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        setupPager();

        txtSearch.addTextChangedListener(filterTextWatcher);

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    //getSearch(txtSearch.getText().toString());
                    setTab();

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
                Intent iNearby = new Intent(getBaseContext(), SearchNearbyActivity.class);
                startActivity(iNearby);
            }
        });

        mListviewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String entry = (String) parent.getAdapter().getItem(position);
                txtSearch.setText(entry);
                setTab();
                //getSearch(entry);
            }
        });

        txtClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });

        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch.setText("");
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

    private void setupViewPager(ViewPager viewPager) {
        adapter = new SearchViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFrag(SearchFragmentEvent.newInstance(0), getString(R.string.header_tab_1));
        adapter.addFrag(SearchFragmentJob.newInstance(1), getString(R.string.header_tab_2));
        adapter.addFrag(SearchFragmentEvent.newInstance(2), getString(R.string.header_tab_3));
        adapter.addFrag(SearchFragmentEvent.newInstance(3), getString(R.string.header_tab_4));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    void setupPager(){
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);


        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

    }

    void setTab(){
        mTabLayout.setScrollPosition(0,0f,true);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
                TextView t = (TextView)tab.getCustomView().findViewById(R.id.txtTabTitle);
                TextView tCount = (TextView)tab.getCustomView().findViewById(R.id.txtTabCount);
                t.setTextColor(tabIconColor);
                tCount.setBackgroundResource(R.drawable.rounded_50dp_primary_color);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorBlack_30);
                TextView t = (TextView)tab.getCustomView().findViewById(R.id.txtTabTitle);
                TextView tCount = (TextView)tab.getCustomView().findViewById(R.id.txtTabCount);
                t.setTextColor(tabIconColor);
                tCount.setBackgroundResource(R.drawable.rounded_50dp_black_30_percent);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
                TextView t = (TextView)tab.getCustomView().findViewById(R.id.txtTabTitle);
                TextView tCount = (TextView)tab.getCustomView().findViewById(R.id.txtTabCount);
                t.setTextColor(tabIconColor);
                tCount.setBackgroundResource(R.drawable.rounded_50dp_primary_color);

            }
        });


        mTabLayout.getTabAt(0).select();

        mResultSearch.setVisibility(View.VISIBLE);
        mExpandSearch.setVisibility(View.GONE);

        ((SearchFragmentEvent)adapter.getItem(0)).getSearch(txtSearch.getText().toString());
        ((SearchFragmentJob)adapter.getItem(1)).getSearch(txtSearch.getText().toString());
    }

    @Override
    public void onCount(int count, int position) {
        TextView tCount = (TextView)mTabLayout.getTabAt(position).getCustomView().findViewById(R.id.txtTabCount);
        tCount.setText(Integer.toString(count));
    }
}