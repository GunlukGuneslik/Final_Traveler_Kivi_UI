package com.example.actualtravellerkiviprojectui.dto;

import com.example.actualtravellerkiviprojectui.SocialMediaPostModel;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDTO implements Serializable {
    private int image;
    private String userName;

    private ArrayList<SocialMediaPostModel> posts;
    ArrayList<String> userLanguages;

    public UserDTO(int image, ArrayList<String> userLanguages, ArrayList<SocialMediaPostModel> posts, String userName) {
        this.image = image;
        this.userLanguages = userLanguages;
        this.posts = posts;
        this.userName = userName;
    }

    public int getImage() {
        return image;
    }

    public ArrayList<String> getUserLanguages() {
        return userLanguages;
    }

    public ArrayList<SocialMediaPostModel> getPosts() {
        return posts;
    }

    public String getUserName() {
        return userName;
    }
}
