package com.example.actualtravellerkiviprojectui.state;

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
}
