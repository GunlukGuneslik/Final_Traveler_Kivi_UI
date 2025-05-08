package com.example.actualtravellerkiviprojectui.api;


import com.example.actualtravellerkiviprojectui.BuildConfig;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;

import retrofit2.Retrofit;

/**
 * ServiceLocator is used to retrieve a reference to common service objects.
 * Services are initialized lazily.
 * See: dependency injection, singleton pattern.
 */
public class ServiceLocator {

    private static Retrofit retrofit;

    private static UserService userService;
    private static PostService postService;
    private static EventService eventService;

    private static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = NetworkModule.provideRetrofit();
        }
        return retrofit;
    }


    public static synchronized UserService getUserService() {
        if (userService == null) {
            userService = NetworkModule.provideUserService(getRetrofit());
        }
        return userService;
    }

    public static synchronized PostService getPostService() {
        if (postService == null) {
            postService = NetworkModule.providePostService(getRetrofit());
        }
        return postService;
    }

    public static synchronized EventService getEventService() {
        if (eventService == null) {
            eventService = NetworkModule.provideEventService(getRetrofit());
        }
        return eventService;
    }
}

