package com.example.actualtravellerkiviprojectui.dto.Event;

import java.util.ArrayList;
import java.util.List;

public class EventSkeletonDTO {

    public Integer id;

    public Integer ownerId;

    public List<EventLocationDTO> locations = new ArrayList<>();

    public EventDTO.EventType type;

    private String details;


}
