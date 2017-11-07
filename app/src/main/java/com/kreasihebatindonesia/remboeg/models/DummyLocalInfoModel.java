package com.kreasihebatindonesia.remboeg.models;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class DummyLocalInfoModel {

    String mName;
    String mAddress;

    public DummyLocalInfoModel(String name, String address){
        mName = name;
        mAddress = address;
    }

    public String getName(){return mName;};

    public String getAddress(){ return mAddress;};
}
