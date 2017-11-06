package com.kreasihebatindonesia.remboeg.models;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class EventModel {

    private int idEvent;
    private String titleEvent;
    private String categoryEvent;
    private String locationEvent;
    private String descEvent;
    private boolean isLikeEvent;
    private boolean isPinEvent;
    private double latEvent;
    private double lngEvent;
    private String venueEvent;
    private String addressEvent;
    private String dateStartEvent;
    private String dateEndEvent;
    private String timeStartEvent;
    private String timeEndEvent;
    private String emailEvent;
    private String websiteEvent;
    private String contactEvent;
    private String ticketEvent;
    private String imageEvent;
    private int totalLikes;
    private int totalViews;
    private int totalShares;

    public EventModel() {
    }

    public EventModel(String title, String image) {
        titleEvent = title;
        imageEvent = image;
    }

    public void setIdEvent(int id) {
        idEvent = id;
    }
    public int getIdEvent() {
        return idEvent;
    }

    public void setTitleEvent(String name) {
        titleEvent = name;
    }
    public String getTitleEvent() {
        return titleEvent;
    }

    public void setCategoryEvent(String category) {
        categoryEvent = category;
    }
    public String getCategoryEvent() { return categoryEvent; }

    public void setLocationEvent(String location) {
        locationEvent = location;
    }
    public String getLocationEvent() { return locationEvent; }

    public void setDescEvent(String desc){
        descEvent = desc;
    }
    public Spanned getDescEvent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(descEvent, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(descEvent);
        }
    }

    public void setLikeEvent(boolean like){
        isLikeEvent = like;
    }
    public boolean getLikeEvent(){
        return isLikeEvent;
    }

    public void setPinEvent(boolean pin){
        isPinEvent = pin;
    }
    public boolean getPinEvent(){
        return isPinEvent;
    }

    public void setLocationEvent(double lat, double lng){
        latEvent = lat;
        lngEvent = lng;
    }
    public double getLocationLatEvent(){
        return latEvent;
    }
    public double getLocationLngEvent(){
        return lngEvent;
    }

    public void setVenueEvent(String venue){
        venueEvent = venue;
    }
    public String getVenueEvent(){
        return venueEvent;
    }

    public void setAddressEvent(String address){
        addressEvent = address;
    }
    public String getAddressEvent(){
        return addressEvent;
    }

    public void setDateStartEvent(String dateStart, boolean formatIndo){

        if(formatIndo){
            dateStartEvent = dateStart;
        }else {
            try {

                // convert string to date
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                Date date = f.parse(dateStart);

                // change the date format
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String result = df.format(date);
                dateStartEvent = result;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public String getDateStartEvent(){
        return dateStartEvent;
    }

    public void setDateEndEvent(String dateEnd, boolean formatIndo){
        if(formatIndo){
            dateEndEvent = dateEnd;
        }else {
            try {
                // convert string to date
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                Date date = f.parse(dateEnd);

                // change the date format
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String result = df.format(date);
                dateEndEvent = result;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public String getDateEndEvent(){
        return dateEndEvent;
    }

    public void setTimeStartEvent(String timeStart){
        timeStartEvent = timeStart;
    }
    public String getTimeStartEvent(){
        return timeStartEvent;
    }

    public void setTimeEndEvent(String timeEnd){
        timeEndEvent = timeEnd;
    }
    public String getTimeEndEvent(){
        return timeEndEvent;
    }

    public void setWebsiteEvent(String website){
        websiteEvent = website;
    }
    public String getWebsiteEvent(){
        return websiteEvent;
    }

    public void setEmailEvent(String email){
        emailEvent = email;
    }
    public String getEmailEvent(){
        return emailEvent;
    }

    public void setContactEvent(String contact){
        contactEvent = contact;
    }
    public String getContactEvent(){
        return contactEvent;
    }

    public void setTicketEvent(String ticket){
        ticketEvent = ticket;
    }
    public String getTicketEvent(){
        return ticketEvent;
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

    public String getImageEvent() {
        return imageEvent;
    }
    public void setImageEvent(String image) {
        imageEvent = image;
    }
}
