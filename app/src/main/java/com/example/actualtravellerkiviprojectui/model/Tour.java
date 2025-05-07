package com.example.actualtravellerkiviprojectui.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.actualtravellerkiviprojectui.dto.PlaceModel;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.util.ArrayList;
import java.util.Date;

public class Tour implements Parcelable {
    private final String tourName;
    private String tourLanguage;
    private final Date date;
    private final int popularity;
    private int tourImage;
    private ArrayList<PlaceModel> places;
    private double rate;
    private String details;

    private ArrayList<String> comments;

    private UserDTO guide;

    public Tour(String destination, Date date, double rate, int popularity, String tourLanguage, ArrayList<PlaceModel> places, UserDTO guide, int tourImage, String details, ArrayList<String> comments) {
        this.tourName = destination;
        this.rate = rate;
        this.date       = date;
        this.tourLanguage = tourLanguage;
        this.popularity = popularity;
        this.places = places;
        this.guide = guide;
        this.tourImage = tourImage;
        this.details = details;
        this.comments = comments;
    }

    public String getTourName() { return tourName; }
    public Date   getDate()        { return date; }

    public String getGuideName() {
        return guide.firstName;
    }
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

    public String getDetails(){ return details; }

    public ArrayList<String> getComments() { return comments;}

    public double getRate() {
        return rate;
    }

    // Parcel constructor
    protected Tour(Parcel in) {
        tourName = in.readString();
        tourLanguage = in.readString();
        date = new Date(in.readLong());
        popularity = in.readInt();
        tourImage = in.readInt();
        rate = in.readDouble();
        places = in.createTypedArrayList(PlaceModel.CREATOR);
        guide = in.readParcelable(UserDTO.class.getClassLoader());
        //sonradan ekledim bir sıkıntı oluşmaz umarım
        details = in.readString();
        comments = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(tourName);
        dest.writeString(tourLanguage);
        dest.writeLong(date.getTime());
        dest.writeInt(popularity);
        dest.writeInt(tourImage);
        dest.writeDouble(rate);
        dest.writeTypedList(places);
        dest.writeParcelable(guide, flags);
        dest.writeString(details);
        dest.writeStringList(comments);
    }

    public static final Creator<Tour> CREATOR = new Creator<Tour>() {
        @Override
        public Tour createFromParcel(Parcel in) {
            return new Tour(in);
        }

        @Override
        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };
}

