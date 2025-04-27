package com.example.actualtravellerkiviprojectui.model;



import java.util.Date;

public class Tour {
    private final String destination;
    private final Date date;
    private final String guideName;
    private final int popularity;

    public Tour(String destination, Date date, String guideName, int popularity) {
        this.destination = destination;
        this.date       = date;
        this.guideName  = guideName;
        this.popularity = popularity;
    }

    public String getDestination() { return destination; }
    public Date   getDate()        { return date; }
    public String getGuideName()   { return guideName; }
    public int    getPopularity()  { return popularity; }
}

