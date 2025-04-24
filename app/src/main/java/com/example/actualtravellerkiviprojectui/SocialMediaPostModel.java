package com.example.actualtravellerkiviprojectui;

public class SocialMediaPostModel {
    String userName;
    String photoDescription;
    String hashtag;
    int profilePhotoId;
    int sharedPhotoId;
    int numberOfLikes;


    public SocialMediaPostModel(String userName, String photoDescription, String hashtag,
                                int profilePhotoId, int sharedPhotoId, int numberOfLikes) {
        this.userName = userName;
        this.photoDescription = photoDescription;
        this.hashtag = hashtag;
        this.profilePhotoId = profilePhotoId;
        this.sharedPhotoId = sharedPhotoId;
        this.numberOfLikes = numberOfLikes;
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
}
