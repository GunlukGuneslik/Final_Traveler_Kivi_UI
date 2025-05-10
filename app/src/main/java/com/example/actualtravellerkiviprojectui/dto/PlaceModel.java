package com.example.actualtravellerkiviprojectui.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * @deprecated migrate to EventDTO and EventLocationDTOs
 */
@Deprecated
public class PlaceModel implements Parcelable{

    private String placeName;
    private String placeInformationText;
    private double rateOfPlace;
    private double distanceInKM;
    int imageOfPlace;
    private String cityName;
    private String districtName;
    private LatLng location;

    public PlaceModel(String placeName, double distanceInKM, double rateOfPlace, String placeInformationText,
                      String cityName, String districtName, LatLng location) {
        this.placeName = placeName;
        this.distanceInKM = distanceInKM;
        this.rateOfPlace = rateOfPlace;
        this.placeInformationText = placeInformationText;
        this.cityName = cityName;
        this.districtName = districtName;
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

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public void setPlaceInformationText(String placeInformationText) {
        this.placeInformationText = placeInformationText;
    }

    // Parcelable Constructor
    protected PlaceModel(Parcel in) {
        placeName = in.readString();
        placeInformationText = in.readString();
        rateOfPlace = in.readDouble();
        distanceInKM = in.readDouble();
        imageOfPlace = in.readInt();
        cityName = in.readString();
        districtName = in.readString();

        // LatLng manually read
        double lat = in.readDouble();
        double lng = in.readDouble();
        location = new LatLng(lat, lng);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(placeName);
        dest.writeString(placeInformationText);
        dest.writeDouble(rateOfPlace);
        dest.writeDouble(distanceInKM);
        dest.writeInt(imageOfPlace);
        dest.writeString(cityName);
        dest.writeString(districtName);

        // LatLng manually write
        dest.writeDouble(location.latitude);
        dest.writeDouble(location.longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel in) {
            return new PlaceModel(in);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };
}
