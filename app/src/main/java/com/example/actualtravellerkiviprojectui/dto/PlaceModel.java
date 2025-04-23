package com.example.actualtravellerkiviprojectui.dto;

public class PlaceModel {

    private String placeName;
    private String placeInformationText;
    private double rateOfPlace;
    private double distanceInKM;
    int imageOfPlace;

    public PlaceModel(String placeName, double distanceInKM, double rateOfPlace, String placeInformationText) {
        this.placeName = placeName;
        this.distanceInKM = distanceInKM;
        this.rateOfPlace = rateOfPlace;
        this.placeInformationText = placeInformationText;
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
}
