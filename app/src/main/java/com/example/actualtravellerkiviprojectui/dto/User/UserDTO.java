package com.example.actualtravellerkiviprojectui.dto.User;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


public class UserDTO implements Parcelable {
    public enum UserType {
        ADMIN,
        REGULAR_USER,
        GUIDE_USER,
        GUEST
    }

    public Set<String> languages = new HashSet<>();
    public LocalDateTime registrationDate;
    public UserType userType;
    public String email;
    public String lastName;
    public String firstName;

    public Integer id;
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

    public UserDTO() {

    }
    public String username;

    protected UserDTO(Parcel in) {
        email = in.readString();
        lastName = in.readString();
        firstName = in.readString();
        if (in.readByte() == 0) { id = null; } else { id = in.readInt(); }
        username = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(lastName);
        dest.writeString(firstName);
        if (id == null) { dest.writeByte((byte) 0); } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(username);
    }
}
