package com.example.actualtravellerkiviprojectui.dto.Event;

/**
 * A point in the map.
 */
public class CoordinateDTO {
    public double latitude;
    public double longtitude;

    public CoordinateDTO() {

    }
    public CoordinateDTO(double longtitude, double latitude) {
        this.longtitude = longtitude;
        this.latitude = latitude;
    }
}
