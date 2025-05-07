package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.api.modules.MockModule;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;

import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;

public class ServiceLocator {
    private static final boolean USE_MOCK = true;

    private static Retrofit retrofit;
    private static MockRetrofit mockRetrofit;

    private static UserService userService;
    private static PostService postService;
    private static EventService eventService;

    private static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = NetworkModule.provideRetrofit();
        }
        return retrofit;
    }

    private static synchronized MockRetrofit getMockRetrofit() {
        if (mockRetrofit == null) {
            mockRetrofit = MockModule.provideMockRetrofit(getRetrofit());
        }

        return mockRetrofit;
    }

    public static synchronized UserService getUserService() {
        if (userService == null) {
            userService = USE_MOCK
                    ? MockModule.provideMockUserService(getMockRetrofit())
                    : NetworkModule.provideUserService(getRetrofit());
        }
        return userService;
    }

    public static synchronized PostService getPostService() {
        if (postService == null) {
            postService = USE_MOCK
                    ? MockModule.provideMockPostService(getMockRetrofit())
                    : NetworkModule.providePostService(getRetrofit());
        }
        return postService;
    }

    public static synchronized EventService getEventService() {
        if (eventService == null) {
            eventService = USE_MOCK
                    ? MockModule.provideMockEventService(getMockRetrofit())
                    : NetworkModule.provideEventService(getRetrofit());
        }
        return eventService;
    }
}

