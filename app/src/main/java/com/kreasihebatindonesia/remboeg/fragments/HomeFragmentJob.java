package com.kreasihebatindonesia.remboeg.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.interfaces.ILocation;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class HomeFragmentJob extends Fragment implements ILocation {


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
        View view = inflater.inflate(R.layout.fragment_event_home, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void getDataByLocationID(int idLocation) {

    }

    @Override
    public void onLocationFinish() {

    }
}
