package com.example.actualtravellerkiviprojectui.dto;

/**
 * An object identifying some image in the database
 */
public class ImageDTO {
    private String id;
    private Integer contentLength;
    private String contentMimeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public void setContentLength(Integer contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentMimeType() {
        return contentMimeType;
    }

    public void setContentMimeType(String contentMimeType) {
        this.contentMimeType = contentMimeType;
    }
}
