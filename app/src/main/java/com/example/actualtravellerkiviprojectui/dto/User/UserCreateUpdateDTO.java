package com.example.actualtravellerkiviprojectui.dto.User;

import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Object for creating and updating users
 */
public class UserCreateUpdateDTO {
    // User information fields
    public String username;
    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public UserDTO.UserType userType;
    public Set<String> languages = new HashSet<>();
    
    public UserCreateUpdateDTO() {
        // Default constructor
    }
}
