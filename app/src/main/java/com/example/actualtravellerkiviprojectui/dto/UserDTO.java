package com.example.actualtravellerkiviprojectui.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.actualtravellerkiviprojectui.model.SocialMediaPostModel;

import java.util.ArrayList;

public class UserDTO implements Parcelable {
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

    public void setImage(int newImage) {
        image = newImage;
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

    // Parcelable constructor
    protected UserDTO(Parcel in) {
        image = in.readInt();
        userName = in.readString();
        userLanguages = in.createStringArrayList();
        posts = in.createTypedArrayList(SocialMediaPostModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(userName);
        dest.writeStringList(userLanguages);
        dest.writeTypedList(posts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserDTO> CREATOR = new Creator<UserDTO>() {
        @Override
        public UserDTO createFromParcel(Parcel in) {
            return new UserDTO(in);
        }

        @Override
        public UserDTO[] newArray(int size) {
            return new UserDTO[size];
        }
    };
}
