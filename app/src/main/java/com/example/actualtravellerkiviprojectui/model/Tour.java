package com.example.actualtravellerkiviprojectui.model;



import com.example.actualtravellerkiviprojectui.dto.PlaceModel;

import java.util.ArrayList;
import java.util.Date;

public class Tour {
    private final String tourName;
    private final Date date;
    private final String guideName;
    private final int popularity;
    private ArrayList<PlaceModel> places;

    public Tour(String destination, Date date, String guideName, int popularity, ArrayList<PlaceModel> places) {
        this.tourName = destination;
        this.date       = date;
        this.guideName  = guideName;
        this.popularity = popularity;
        this.places = places;
    }

    public String getTourName() { return tourName; }
    public Date   getDate()        { return date; }
    public String getGuideName()   { return guideName; }
    public int    getPopularity()  { return popularity; }
    //@author Güneş
    public ArrayList<PlaceModel> getPlaces() {
        return places;
    }
}

