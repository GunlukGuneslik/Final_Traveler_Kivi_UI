package com.example.actualtravellerkiviprojectui.model;



import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Tour implements Serializable {
    private final String tourName;
    private String tourLanguage;
    private final Date date;
    private final int popularity;
    private int tourImage;
    private ArrayList<PlaceModel> places;
    private double rate;

    private UserDTO guide;

    public Tour(String destination, Date date, double rate, int popularity, String tourLanguage, ArrayList<PlaceModel> places, UserDTO guide, int tourImage) {
        this.tourName = destination;
        this.rate = rate;
        this.date       = date;
        this.tourLanguage = tourLanguage;
        this.popularity = popularity;
        this.places = places;
        this.guide = guide;
        this.tourImage = tourImage;
    }

    public String getTourName() { return tourName; }
    public Date   getDate()        { return date; }
    public String getGuideName()   { return guide.getUserName(); }
    public int    getPopularity()  { return popularity; }
    //@author Güneş
    public ArrayList<PlaceModel> getPlaces() {
        return places;
    }
    //@ Güneş
    public UserDTO getGuide() {
        return guide;
    }

    public int getTourImage() {
        return tourImage;
    }

    public String getTourLanguage(){
        return tourLanguage;
    }

    public double getRate() {
        return rate;
    }
}

