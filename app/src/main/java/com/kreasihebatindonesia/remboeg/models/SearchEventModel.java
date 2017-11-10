package com.kreasihebatindonesia.remboeg.models;

/**
 * Created by IT DCM on 08/11/2017.
 */

public class SearchEventModel {

    private int mId;
    private int mIdType;
    private String mTitle;
    private String mImage;
    private String mTicket;
    private String mAddress;

    public SearchEventModel(){

    }

    public void setId(int id){
        mId = id;
    }
    public int getId(){return mId;}

    public void setIdType(int idtype){
        mIdType = idtype;
    }
    public int getIdType(){ return mIdType;}

    public void setTitle(String title){
        mTitle = title;
    }
    public String getTitle(){ return mTitle;}

    public void setImage(String image){
        mImage = image;
    }
    public String getImage(){ return mImage;}

    public void setTicket(String ticket){
        mTicket = ticket;
    }
    public String getTicket(){ return mTicket;}

    public void setAddress(String address){
        mAddress = address;
    }
    public String getAddress(){ return mAddress;}
}
