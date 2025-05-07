package com.example.actualtravellerkiviprojectui.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.PostDTO;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author zeynep
 */
public class SocialMediaPostModel implements Parcelable {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();
    private static SocialMediaPostModel created;
    UserDTO owner;
    String photoDescription;
    List<String> hashtags;
    int profilePhotoId;
    int sharedPhotoId;
    int numberOfLikes;
    Date sharedDate;


    public SocialMediaPostModel(UserDTO owner, String photoDescription, List<String> hashtags,
                                List<String> pictureIDs, int numberOfLikes, Date sharedDate) {
        this.owner = owner;
        this.photoDescription = photoDescription;
        this.hashtags = hashtags;
        this.profilePhotoId = profilePhotoId;
        this.sharedPhotoId = sharedPhotoId;
        this.numberOfLikes = numberOfLikes;
        this.sharedDate = sharedDate;
    }


    protected SocialMediaPostModel(Parcel in) {
        photoDescription = in.readString();
        hashtags = in.createStringArrayList();
        profilePhotoId = in.readInt();
        sharedPhotoId = in.readInt();
        numberOfLikes = in.readInt();
    }

    @NonNull
    public static SocialMediaPostModel fromPostDTO(PostDTO postDTO) {
        UserDTO owner = null;
        try {
            owner = userService.getUser(postDTO.userId).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SocialMediaPostModel created = new SocialMediaPostModel(owner, "a nice photo", postDTO.tags, postDTO.imageIds, postDTO.likeCount, new Date(postDTO.createdAt));
        return created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SocialMediaPostModel> CREATOR = new Creator<SocialMediaPostModel>() {
        @Override
        public SocialMediaPostModel createFromParcel(Parcel in) {
            return new SocialMediaPostModel(in);
        }

        @Override
        public SocialMediaPostModel[] newArray(int size) {
            return new SocialMediaPostModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(photoDescription);
        dest.writeStringList(hashtags);
        dest.writeInt(profilePhotoId);
        dest.writeInt(sharedPhotoId);
        dest.writeInt(numberOfLikes);
    }

    public String getPhotoDescription() {
        return photoDescription;
    }

    public List<String> getHashtags() {
        return hashtags;
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

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public String getSharedDate() {
        return "Date: " + sharedDate;
    }



}
