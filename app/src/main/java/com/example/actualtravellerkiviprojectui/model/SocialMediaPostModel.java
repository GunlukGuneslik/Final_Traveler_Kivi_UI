package com.example.actualtravellerkiviprojectui.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.ServiceLocator;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.PostDTO;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.util.Date;

/**
 * @author zeynep
 */
public class SocialMediaPostModel implements Parcelable {
    private static final UserService userService = ServiceLocator.getUserService();
    private static final PostService postService = ServiceLocator.getPostService();
    private static final EventService eventService = ServiceLocator.getEventService();
    private static SocialMediaPostModel created;
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

    public static SocialMediaPostModel fromPostDTO(PostDTO postDTO) {
        UserDTO owner = userService.getUser(postDTO.userId);
        SocialMediaPostModel created = new SocialMediaPostModel(owner.firstName, "a nice photo", postDTO.tags, postService.getLikeCount(postDTO.postId), postDTO.createdAt)
        return created;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public String getSharedDate() {
        return "Date: " + sharedDate;
    }

    // Parcelable Constructor
    protected SocialMediaPostModel(Parcel in) {
        userName = in.readString();
        photoDescription = in.readString();
        hashtag = in.readString();
        profilePhotoId = in.readInt();
        sharedPhotoId = in.readInt();
        numberOfLikes = in.readInt();
        long dateMillis = in.readLong();
        sharedDate = new Date(dateMillis);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(photoDescription);
        dest.writeString(hashtag);
        dest.writeInt(profilePhotoId);
        dest.writeInt(sharedPhotoId);
        dest.writeInt(numberOfLikes);
        dest.writeLong(sharedDate.getTime());
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
}
