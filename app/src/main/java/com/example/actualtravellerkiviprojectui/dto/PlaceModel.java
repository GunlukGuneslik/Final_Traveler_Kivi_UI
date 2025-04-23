package com.example.actualtravellerkiviprojectui.dto;

import com.google.android.gms.maps.model.LatLng;

public class PlaceModel {

    private String placeName;
    private String placeInformationText;
    private double rateOfPlace;
    private double distanceInKM;
    int imageOfPlace;
    private LatLng location;

    public PlaceModel(String placeName, double distanceInKM, double rateOfPlace, String placeInformationText, LatLng location) {
        this.placeName = placeName;
        this.distanceInKM = distanceInKM;
        this.rateOfPlace = rateOfPlace;
        this.placeInformationText = placeInformationText;
        this.location = location;
    }

    public String getPlaceName() {
        return placeName;
    }
    public String getPlaceInformationText(){
        return placeInformationText;
    }
    public String getRateOfPlace(){
        return "Rate: " + rateOfPlace;
    }
    public String getDistanceInKM() {
        return "Distance: " + distanceInKM + " km";
    }
    public int getImageOfPlace() {
        return imageOfPlace;
    }
    public LatLng getLocation() {
        return location;
    }
}
