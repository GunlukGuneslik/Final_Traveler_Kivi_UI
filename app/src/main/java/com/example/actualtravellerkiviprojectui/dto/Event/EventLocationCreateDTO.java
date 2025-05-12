package com.example.actualtravellerkiviprojectui.dto.Event;


import java.util.HashSet;
import java.util.Set;

public class EventLocationCreateDTO {
    public CoordinateDTO location;

    public String city;
    public String district;

    public String title;

    public String description;

    public Set<String> keywords = new HashSet<>();

    public boolean featured;

    public EventLocationCreateDTO(CoordinateDTO location, boolean featured, Set<String> keywords, String description, String title, String district, String city) {
        this.location = location;
        this.featured = featured;
        this.keywords = keywords;
        this.description = description;
        this.title = title;
        this.district = district;
        this.city = city;
    }
}