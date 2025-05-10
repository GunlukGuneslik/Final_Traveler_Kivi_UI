package com.example.actualtravellerkiviprojectui.dto.Event;


import java.time.LocalDate;

/* EventComment DTO.
 * Also used for Event Chat posts
 */
public class EventCommentDTO {
    public Integer id;
    public Integer ownerId;
    public LocalDate commentDate;
    public String commentBody;
}

