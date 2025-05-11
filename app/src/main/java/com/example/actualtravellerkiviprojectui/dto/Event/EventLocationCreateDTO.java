package com.example.actualtravellerkiviprojectui.dto.Event;


import java.util.HashSet;
import java.util.Set;

public class EventLocationCreateDTO {
    public CoordinateDTO location;

    public String title;

    public String description;

    public Set<String> keywords = new HashSet<>();

    public boolean featured;
}