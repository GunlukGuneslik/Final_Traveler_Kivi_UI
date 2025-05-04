package com.example.actualtravellerkiviprojectui.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO
 */
public class PostDTO {

    public Integer postId; // Unique identifier for the post
    public Integer userId; // ID of the user who created the post
    public String body; // Content of the post
    public List<String> imageIds = new ArrayList<>(); // List of image URLs associated with the post
    public List<Integer> tagIds = new ArrayList<>(); // List of tags associated with the post
    public String createdAt; // Timestamp for when the post was created
    public String updatedAt; // Timestamp for when the post was last updated
}