package com.example.actualtravellerkiviprojectui.dto.Post;

import java.util.ArrayList;
import java.util.List;

public class PostCreateDTO {

    // TODO: will remove when we migrate to spring security
    public Integer userId;

    public String body;

    // null if no image
    public byte[] image = null;

    public List<String> tags = new ArrayList<>();

}
