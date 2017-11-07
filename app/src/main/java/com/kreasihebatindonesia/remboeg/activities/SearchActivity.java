package com.kreasihebatindonesia.remboeg.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.kreasihebatindonesia.remboeg.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtNearby)
    TextView txtNearby;


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

        txtNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iNearby = new Intent(getBaseContext(), NearbyActivity.class);
                startActivity(iNearby);
            }
        });
    }
}