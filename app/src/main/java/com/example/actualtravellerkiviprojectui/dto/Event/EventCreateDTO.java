package com.example.actualtravellerkiviprojectui.dto.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * DTO for creating a new Event.
 */
public class EventCreateDTO {

    /**
     * Maps necessary objects to create a Event entity.
     *
     * @param dto
     * @param owner
     * @param locations
     * @return
     */

    public Integer ownerId;
    public EventDTO.EventType eventType;
    public String name;
    public String details;
    public LocalDate startDate;
    public double rate;
    public int popularity;
    public String language;
    public Integer skeletonId;

    public List<EventLocationCreateDTO> locations = new ArrayList<>();

    public EventCreateDTO(Integer ownerId, String name, String details, LocalDate startDate, Integer skeletonId, int popularity,
                          double rate, String language, List<EventLocationCreateDTO> locations) {
        this.ownerId = ownerId;
        this.name = name;
        this.details = details;
        this.startDate = startDate;
        this.skeletonId = skeletonId;
        this.rate = rate;
        this.language = language;
        this.popularity = popularity;
        this.locations = locations;
    }
}