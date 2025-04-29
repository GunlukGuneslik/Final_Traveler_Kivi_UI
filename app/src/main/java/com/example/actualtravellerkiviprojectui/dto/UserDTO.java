package com.example.actualtravellerkiviprojectui.dto;

import java.util.Set;

public class UserDTO {
    public enum UserType {
        ADMIN,
        REGULAR_USER,
        GUIDE_USER,
        GUEST
    }

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    public String getEmail() {
        return email;
    }


    private UserType userType;

    private String registrationDate;

    private Byte[] profilePicture;

    private Set<String> languages;


    public Set<String> getLanguages() {
        return languages;
    }

    public UserDTO() {
    }

    public Byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getId() {
        return id;
    }


    public UserType getUserType() {
        return userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

}