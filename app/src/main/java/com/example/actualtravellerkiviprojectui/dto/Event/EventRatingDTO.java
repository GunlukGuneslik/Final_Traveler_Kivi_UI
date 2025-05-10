package com.example.actualtravellerkiviprojectui.dto.Event;

import java.time.LocalDateTime;

/**
 * An object representing a rating submitted to a tour.
 */
public class EventRatingDTO {

    public Integer id;
    public Integer ownerId;
    public Integer eventId;
    public Integer rate;
    public String comment;
    public LocalDateTime date;

}
