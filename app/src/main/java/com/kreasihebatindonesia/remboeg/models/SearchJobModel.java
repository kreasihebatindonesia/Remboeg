package com.kreasihebatindonesia.remboeg.models;

/**
 * Created by InfinityLogic on 11/10/2017.
 */

public class SearchJobModel {

    private int mId;
    private String mTitle;
    private String mImage;
    private String mAddress;
    private String mSalary;

    public SearchJobModel(){

    }


    public void setId(int id){
        mId = id;
    }
    public int getId(){return mId;}

    public void setTitle(String title){
        mTitle = title;
    }
    public String getTitle(){ return mTitle;}

    public void setImage(String image){
        mImage = image;
    }
    public String getImage(){ return mImage;}

    public void setSalary(String salary){
        mSalary = salary;
    }
    public String getSalary(){ return mSalary;}

    public void setAddress(String address){
        mAddress = address;
    }
    public String getAddress(){ return mAddress;}
}
