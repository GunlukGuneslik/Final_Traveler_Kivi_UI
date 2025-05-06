package com.example.actualtravellerkiviprojectui.api.modules;

import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.UserService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class NetworkModule {
    private static final String BASE_URL = "https://kivi.local:45976/";

    public static Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    public static PostService providePostService(Retrofit retrofit) {
        return retrofit.create(PostService.class);
    }

    public static EventService provideEventService(Retrofit retrofit) {
        return retrofit.create(EventService.class);
    }
}
