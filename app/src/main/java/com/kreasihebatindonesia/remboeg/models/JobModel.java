package com.kreasihebatindonesia.remboeg.models;

/**
 * Created by InfinityLogic on 11/10/2017.
 */

public class JobModel {
    private int mId;
    private String mTitle;
    private String mImage;
    private String mEndDate;
    private String mSalary;
    private int mTotalLike;
    private int mTotalView;
    private int mTotalShare;

    public void setId(int id){
        mId = id;
    }
    public int getId(){ return mId;}

    public void setTitle(String title){
        mTitle = title;
    }
    public String getTitle(){ return mTitle;}

    public void setImage(String image){
        mImage = image;
    }
    public String getImage(){ return mImage; }

    public void setEndDate(String endDate){
        mEndDate = endDate;
    }
    public String getEndDate(){ return mEndDate; }

    public void setSalary(String salary){
        mSalary = salary;
    }
    public String getSalary(){ return mSalary; }

    public void setTotalLike(int totallike){
        mTotalLike = totallike;
    }
    public int getTotalLike(){ return mTotalLike;}

    public void setTotalView(int totalview){
        mTotalView = totalview;
    }
    public int getTotalView(){ return mTotalView;}

    public void setTotalShare(int totalshare){
        mTotalShare = totalshare;
    }
    public int getTotalShare(){ return mTotalShare;}
}
