package com.example.actualtravellerkiviprojectui.api.modules;

import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.api.mock.MockEventService;
import com.example.actualtravellerkiviprojectui.api.mock.MockPostService;
import com.example.actualtravellerkiviprojectui.api.mock.MockUserService;

import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * MockModule is only used in {@link com.example.actualtravellerkiviprojectui.api.ServiceLocator}
 */
public class MockModule {
    public static MockRetrofit provideMockRetrofit(Retrofit retrofit) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setDelay(0, TimeUnit.SECONDS);
        return new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior)
                .build();
    }

    public static UserService provideMockUserService(MockRetrofit mockRetrofit) {
        BehaviorDelegate<UserService> delegate = mockRetrofit.create(UserService.class);
        return new MockUserService(delegate);
    }

    public static EventService provideMockEventService(MockRetrofit mockRetrofit) {
        BehaviorDelegate<EventService> delegate = mockRetrofit.create(EventService.class);
        return new MockEventService(delegate);
    }

    public static PostService provideMockPostService(MockRetrofit mockRetrofit) {
        BehaviorDelegate<PostService> delegate = mockRetrofit.create(PostService.class);
        return new MockPostService(delegate);
    }
}

