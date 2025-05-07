package com.example.actualtravellerkiviprojectui.api;


import com.example.actualtravellerkiviprojectui.BuildConfig;
import com.example.actualtravellerkiviprojectui.api.modules.MockModule;
import com.example.actualtravellerkiviprojectui.api.modules.NetworkModule;

import retrofit2.Retrofit;
import retrofit2.mock.MockRetrofit;

/**
 * ServiceLocator is used to retrieve a reference to common service objects.
 * Services are initialized lazily.
 * See: dependency injection, singleton pattern.
 */
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
            userService = BuildConfig.FLAVOR.equals("mock")
                    ? MockModule.provideMockUserService(getMockRetrofit())
                    : NetworkModule.provideUserService(getRetrofit());
        }
        return userService;
    }

    public static synchronized PostService getPostService() {
        if (postService == null) {
            postService = BuildConfig.FLAVOR.equals("mock")
                    ? MockModule.provideMockPostService(getMockRetrofit())
                    : NetworkModule.providePostService(getRetrofit());
        }
        return postService;
    }

    public static synchronized EventService getEventService() {
        if (eventService == null) {
            eventService = BuildConfig.FLAVOR.equals("mock")
                    ? MockModule.provideMockEventService(getMockRetrofit())
                    : NetworkModule.provideEventService(getRetrofit());
        }
        return eventService;
    }
}

