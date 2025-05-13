package com.example.actualtravellerkiviprojectui.dto.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventDTO {
    public Integer id;
    public EventType eventType;
    public Status status;
    public LocalDate created;
    public LocalDateTime startDate;
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
    public Double rating;

    public enum EventType {
        TOUR, MEETUP
    }

    public enum Status {
        SCHEDULED, FINISHED, CANCELLED,
    }

    public EventDTO() {

    }
    public EventDTO(Integer ownerId, String name, String details, LocalDateTime startDate, Integer skeletonId,
                          double rating, String language, List<EventLocationDTO> locations) {
        this.ownerId = ownerId;
        this.name = name;
        this.details = details;
        this.startDate = startDate;
        this.skeletonId = skeletonId;
        this.rating = rating;
        this.language = language;
        this.locations = locations;
    }

}
