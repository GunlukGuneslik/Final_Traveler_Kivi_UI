package com.example.actualtravellerkiviprojectui.dto.Post;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO
 */
public class PostDTO {

    public Integer postId; // Unique identifier for the post
    public Integer userId; // ID of the user who created the post
    public String body; // Content of the post
    public Integer imageId; // List of image URLs associated with the post
    public List<String> tags = new ArrayList<>(); // List of tags associated with the post
    public LocalDate createdAt; // Timestamp for when the post was created
    public Integer likeCount;
}