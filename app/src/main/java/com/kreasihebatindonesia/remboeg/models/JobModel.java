package com.kreasihebatindonesia.remboeg.models;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by InfinityLogic on 11/10/2017.
 */

public class JobModel {

    public class PositionModel{
        private int idPosition;
        private String namePosition;
        private String descPosition;


        public PositionModel(int id, String name, String desc) {
            idPosition = id;
            namePosition = name;
            descPosition = desc;
        }

        public void setIdPosition(int id){
            idPosition = id;
        }

        public int getIdPosition(){
            return idPosition;
        }

        public void setNamePosition(String namePos){
            namePosition = namePos;
        }
        public String getNamePosition(){
            return namePosition;
        }

        public void setDescPosition(String desc){
            descPosition = desc;
        }
        public Spanned getDescPosition(){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(descPosition, Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(descPosition);
            }
        }
    }

    private int idJob;
    private String titleJob;
    private String descJob;
    private String benefitJob;
    private String imageJob;
    private double latJob;
    private double lngJob;
    private String companyJob;
    private String addressJob;
    private String salaryJob;
    private String contactJob;
    private String emailJob;
    private String websiteJob;
    private String endDateJob;
    private int totalLikes;
    private int totalViews;
    private int totalShares;
    private boolean viaEmailJob;
    private boolean viaPostJob;
    private boolean viaOfficeJob;
    private boolean alreadyLikeJob;

    private List<PositionModel> positionModels = new ArrayList<>();

    public void setIdJob(int id) {
        idJob = id;
    }
    public int getIdJob() {
        return idJob;
    }

    public void setTitleJob(String name) {
        titleJob = name;
    }
    public String getTitleJob() {
        return titleJob;
    }

    public void setDescEvent(String desc){
        descJob = desc;
    }
    public Spanned getDescEvent(){

        if(descJob == null)
            return null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(descJob, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(descJob);
        }
    }

    public void setBenefitJob(String benefit) {
        benefitJob = benefit;
    }
    public Spanned getBenefitJob() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(benefitJob, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(benefitJob);
        }
    }

    public void setImageJob(String image) {
        imageJob = image;
    }
    public String getImageJob() {
        return imageJob;
    }

    public void setLocationJob(double lat, double lng){
        latJob = lat;
        lngJob = lng;
    }
    public double getLocationLatJob(){
        return latJob;
    }
    public double getLocationLngJob(){
        return lngJob;
    }

    public void setCompanyJob(String company) {
        companyJob = company;
    }
    public String getCompanyJob() {
        return companyJob;
    }

    public void setAddressJob(String address) {
        addressJob = address;
    }
    public String getAddressJob() {
        return addressJob;
    }

    public void setSalaryJob(String salary) {
        salaryJob = salary;
    }
    public String getSalaryJob() {
        return salaryJob;
    }

    public void setContactJob(String contact) {
        contactJob = contact;
    }
    public String getContactJob() {
        return contactJob;
    }

    public void setEmailJob(String email) {
        emailJob = email;
    }
    public String getEmailJob() {
        return emailJob;
    }

    public void setWebsiteJob(String website) {
        websiteJob = website;
    }
    public String getWebsiteJob() {
        return websiteJob;
    }

    public void setEndDateJob(String end) {
        endDateJob = end;
    }
    public String getEndDateJob() {
        return endDateJob;
    }

    public void setTotalLikes(int likes){
        totalLikes = likes;
    }
    public int getTotalLikes(){
        return totalLikes;
    }

    public void setTotalViews(int views){
        totalViews = views;
    }
    public int getTotalViews(){
        return totalViews;
    }

    public void setTotalShares(int share){
        totalShares = share;
    }
    public int getTotalShares(){
        return totalShares;
    }

    public void setViaEmail (boolean email){
        viaEmailJob = email;
    }
    public boolean getViaEmail(){
        return viaEmailJob;
    }

    public void setViaPost (boolean post){
        viaPostJob = post;
    }
    public boolean getViaPost(){
        return viaPostJob;
    }

    public void setViaOffice (boolean office){
        viaOfficeJob = office;
    }
    public boolean getViaOffice(){
        return viaOfficeJob;
    }

    public void setAlreadyLikeJob (boolean like_already){
        alreadyLikeJob = like_already;
    }
    public boolean getAlreadyLikeJob(){
        return alreadyLikeJob;
    }

    public void setPositionModels(int id, String name, String desc){
        PositionModel posModel = new PositionModel(id, name, desc);
        positionModels.add(posModel);
    }

    public List<PositionModel> getPositionModels(){
        return positionModels;
    }
}
