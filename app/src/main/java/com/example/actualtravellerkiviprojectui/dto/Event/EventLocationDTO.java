package com.example.actualtravellerkiviprojectui.dto.Event;

import java.util.Set;

/**
 * Point of attraction.
 * (E.g. Anıtkabir, Kızılay)
 */
public class EventLocationDTO {
    public Integer id;
    public String title;
    public String description;

    public String city;
    public String district;
    public Set<String> keywords;
    public CoordinateDTO location;
    public boolean featured;

    public EventLocationDTO() {

    }

    public EventLocationDTO(CoordinateDTO location, boolean featured, Set<String> keywords, String description, String title, String district, String city) {
        this.location = location;
        this.featured = featured;
        this.keywords = keywords;
        this.description = description;
        this.title = title;
        this.district = district;
        this.city = city;
    }
}
