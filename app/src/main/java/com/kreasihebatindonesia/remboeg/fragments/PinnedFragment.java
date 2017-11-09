package com.kreasihebatindonesia.remboeg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreasihebatindonesia.remboeg.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by IT DCM on 09/11/2017.
 */

public class PinnedFragment extends Fragment {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    public static PinnedFragment newInstance(int index) {
        PinnedFragment fragment = new PinnedFragment();
        Bundle b = new Bundle();
        b.putInt("index", index);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pinned, container, false);

        ButterKnife.bind(this, view);

        mToolbar.setTitle("Pinned");

        return view;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
