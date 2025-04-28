package com.example.actualtravellerkiviprojectui.dto;
import java.util.List;
import java.util.ArrayList;
public class PostDTO {

    // TODO: will remove when we migrate to spring security
    //@NotNull // TODO: add annotations when we add the jackson dependency
    private Integer userId;

    //@NotBlank
    private String body;

    private List<String> images = new ArrayList<>();

    private List<String> tags = new ArrayList<>();

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
