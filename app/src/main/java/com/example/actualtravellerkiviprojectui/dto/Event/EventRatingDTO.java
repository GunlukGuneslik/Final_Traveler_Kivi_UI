package com.example.actualtravellerkiviprojectui.dto.Event;

import java.time.LocalDate;

/**
 * An object representing a rating submitted to a tour.
 */
public class EventRatingDTO {

    public Integer id;
    public Integer eventId;
    public Integer rate;
    public String comment;
    public LocalDate date;

}
