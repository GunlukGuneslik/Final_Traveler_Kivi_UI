package com.example.actualtravellerkiviprojectui.dto.Event;

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
    public List<EventLocationDTO> locations = new ArrayList<>();
    public List<Integer> userIds = new ArrayList<>();
    public Integer ownerId;
    public String name;
    public String details;
    public List<Integer> commentIds = new ArrayList<>();
    public Integer skeletonId;
    public Integer imageId;
    public String language;
    public Integer rating;

    public enum EventType {
        TOUR, MEETUP
    }

    public enum Status {
        SCHEDULED, FINISHED, CANCELLED,
    }

    public EventDTO() {

    }

}
