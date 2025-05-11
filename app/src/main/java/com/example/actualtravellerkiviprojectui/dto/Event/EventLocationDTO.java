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
    public Set<String> keywords;
    public CoordinateDTO location;
    public boolean featured;
}
