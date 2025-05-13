package com.example.actualtravellerkiviprojectui.dto.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the template for recurring events.
 * You create new events based on previous ones by using their skeleton id's.
 */
public class EventSkeletonDTO {

    public Integer id;

    public Integer ownerId;

    public List<EventLocationDTO> locations = new ArrayList<>();

    public EventDTO.EventType type;

    private String details;


}
