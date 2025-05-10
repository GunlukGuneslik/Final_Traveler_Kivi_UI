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

    public LocalDate endDate;

    public Integer skeletonId;

    public List<Integer> locationIds = new ArrayList<>();

}