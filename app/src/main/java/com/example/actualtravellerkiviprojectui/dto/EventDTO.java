package com.example.actualtravellerkiviprojectui.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventDTO {
    public Integer id;
    public EventType eventType;
    public Status status;
    public LocalDate created;
    public LocalDate startDate;
    public LocalDate endDate;
    public Set<Integer> ratingIds = new HashSet<>();
    public List<Integer> locationIds = new ArrayList<>();
    public List<Integer> userIds = new ArrayList<>();
    public Integer ownerId;
    public String name;
    public String details;
    public List<Integer> commentIds = new ArrayList<>();
    public enum EventType {
        TOUR,
        MEETUP
    }
    public enum Status {
        SCHEDULED,
        FINISHED,
        CANCELLED,
    }

}

