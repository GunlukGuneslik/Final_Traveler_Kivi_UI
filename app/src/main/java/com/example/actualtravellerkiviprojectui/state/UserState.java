package com.example.actualtravellerkiviprojectui.state;

import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;

import java.io.IOException;

/**
 * Singleton persisting the user state.
 */
public class UserState {
    private static Integer userId = 1;


    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        UserState.userId = userId;
    }

    public static UserDTO getUser(UserService userService) {
        try {
            return userService.getUser(userId).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
