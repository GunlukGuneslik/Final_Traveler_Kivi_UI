package com.example.actualtravellerkiviprojectui.dto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class PostDTO {

    // TODO: will remove when we migrate to spring security
    //@NotNull // TODO: add annotations when we add the jackson dependency
    private Integer id;
    private UserDTO owner;

    //@NotBlank
    private String body;

    private List<ImageDTO> images = new ArrayList<ImageDTO>();

    private List<TagDTO> tags = new ArrayList<TagDTO>();

    private Set<UserDTO> likers = new HashSet<>();
//TODO: LocalDate
    private String created;
    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<ImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ImageDTO> images) {
        this.images = images;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<UserDTO> getLikers() {
        return likers;
    }

    public void setLikers(Set<UserDTO> likers) {
        this.likers = likers;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
