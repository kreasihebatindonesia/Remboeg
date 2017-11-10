package com.kreasihebatindonesia.remboeg.models;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class NearbyModel {
    private int mId;
    private int mIdType;
    private int mIdLocation;
    private String mTitle;
    private double mLatLoc;
    private double mLngLoc;
    private String mVenue;
    private String mAddress;
    private String mImage;
    private String mTicket;
    private String mSalary;

    public NearbyModel(){

    }

    public void setId(int id){
        mId = id;
    }
    public int getId(){
        return mId;
    }

    public void setIdType(int idtype){
        mIdType = idtype;
    }
    public int getIdType(){
        return mIdType;
    }

    public void setIdLocation(int idloc){
        mIdLocation = idloc;
    }
    public int getIdLocation(){
        return mIdLocation;
    }

    public void setTitle(String title){
        mTitle = title;
    }
    public String getTitle(){
        return mTitle;
    }

    public void setLocation(double lat, double lng){
        mLatLoc = lat;
        mLngLoc = lng;
    }
    public double getLatLocation(){
        return mLatLoc;
    }
    public double getLngLocation(){
        return mLngLoc;
    }

    public void setVenue(String venue){
        mVenue = venue;
    }
    public String getVenue(){
        return mVenue;
    }

    public void setAddress(String address){
        mAddress = address;
    }
    public String getAddress(){
        return mAddress;
    }

    public String getImage() {
        return mImage;
    }
    public void setImage(String image) {
        mImage = image;
    }

    public void setTicket(String ticket){
        mTicket = ticket;
    }
    public String getTicket(){
        return mTicket;
    }

    public void setSalary(String salary){
        mSalary = salary;
    }
    public String getSalary(){
        return mSalary;
    }
}
