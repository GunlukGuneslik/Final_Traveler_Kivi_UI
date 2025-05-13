package com.example.actualtravellerkiviprojectui.dto.User;

import java.util.Set;

public class UserCreateUpdateDTO {
    public String email;
    public String password;
    public String username;
    public String firstName;
    public String lastName;
    public Set<String> languages;
    public UserDTO.UserType userType;

    public UserCreateUpdateDTO(String email, String password, String username, String firstName, String lastName,
                               Set<String> languages, UserDTO.UserType userType) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.languages = languages;
        this.userType = userType;
    }

    public UserCreateUpdateDTO() {
        // Default constructor
    }

}
