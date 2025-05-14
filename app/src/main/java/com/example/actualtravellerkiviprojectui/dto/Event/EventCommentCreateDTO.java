package com.example.actualtravellerkiviprojectui.dto.Event;

public class EventCommentCreateDTO {
    public Integer ownerId;
    public String comment;

    public EventCommentCreateDTO() {

    }

    public EventCommentCreateDTO(Integer ownerId, String comment) {
        this.comment = comment;
        this.ownerId = ownerId;
    }

    public static EventCommentDTO toEventCommentDTO(EventCommentCreateDTO eventCommentCreateDTO) {
        EventCommentDTO eventCommentDTO = new EventCommentDTO();
        eventCommentDTO.ownerId = eventCommentCreateDTO.ownerId;
        eventCommentDTO.commentBody = eventCommentCreateDTO.comment;
        return eventCommentDTO;
    }
}
