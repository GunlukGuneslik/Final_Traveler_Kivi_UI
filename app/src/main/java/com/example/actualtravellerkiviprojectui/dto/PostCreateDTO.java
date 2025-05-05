package com.example.actualtravellerkiviprojectui.dto;

import java.util.ArrayList;
import java.util.List;

public class PostCreateDTO {

    // TODO: will remove when we migrate to spring security
    public Integer userId;

    public String body;

    public List<String> images = new ArrayList<>();

    public List<String> tags = new ArrayList<>();

}
