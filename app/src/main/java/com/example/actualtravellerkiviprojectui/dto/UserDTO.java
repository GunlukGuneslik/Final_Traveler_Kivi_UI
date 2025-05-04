package com.example.actualtravellerkiviprojectui.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class UserDTO {
    public enum UserType {
        ADMIN,
        REGULAR_USER,
        GUIDE_USER,
        GUEST
    }

    public Set<String> languages = new HashSet<>();
    public LocalDate registrationDate;
    public UserType userType;
    public String email;
    public String lastName;
    public String firstName;
    public Integer id;

}
