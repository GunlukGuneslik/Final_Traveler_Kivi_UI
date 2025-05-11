package com.example.actualtravellerkiviprojectui.model;

/**
 * @author zeynep
 */
public class Message {
    private final String userId;
    private final String text;
    private long timestamp;

    public Message(String userId, String text,long timestamp){
        this.userId = userId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }
    public String getText() {
        return text;
    }
    public long getTimestamp() {
        return timestamp;
    }
}
