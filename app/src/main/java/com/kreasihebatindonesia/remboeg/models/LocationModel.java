package com.kreasihebatindonesia.remboeg.models;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class LocationModel {
    private int mId;
    private String mName;

    public LocationModel(int id, String name){
        mId = id;
        mName = name;
    }

    public int GetIdCity(){
        return mId;
    }

    public String GetNameCity(){
        return mName;
    }
}
