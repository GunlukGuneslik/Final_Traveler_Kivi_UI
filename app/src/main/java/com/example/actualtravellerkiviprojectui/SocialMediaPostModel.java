package com.example.actualtravellerkiviprojectui;

import java.util.Date;

public class SocialMediaPostModel {
    String userName;
    String photoDescription;
    String hashtag;
    int profilePhotoId;
    int sharedPhotoId;
    int numberOfLikes;
    Date sharedDate;


    public SocialMediaPostModel(String userName, String photoDescription, String hashtag,
                                int profilePhotoId, int sharedPhotoId, int numberOfLikes, Date sharedDate) {
        this.userName = userName;
        this.photoDescription = photoDescription;
        this.hashtag = hashtag;
        this.profilePhotoId = profilePhotoId;
        this.sharedPhotoId = sharedPhotoId;
        this.numberOfLikes = numberOfLikes;
        this.sharedDate = sharedDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhotoDescription() {
        return photoDescription;
    }

    public String getHashtag() {
        return hashtag;
    }

    public int getProfilePhotoId() {
        return profilePhotoId;
    }

    public int getSharedPhotoId() {
        return sharedPhotoId;
    }

    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public String getSharedDate(){return "Date: " + sharedDate;}
}
