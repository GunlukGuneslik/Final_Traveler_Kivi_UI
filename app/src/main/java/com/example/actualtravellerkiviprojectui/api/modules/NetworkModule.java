package com.example.actualtravellerkiviprojectui.api.modules;

import com.example.actualtravellerkiviprojectui.App;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.UserService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * NetworkModule is only used in {@link com.example.actualtravellerkiviprojectui.api.ServiceLocator}
 */
public class NetworkModule {
    private static final String BASE_URL = App.getContext().getResources().getString(R.string.kivi_api_url);

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

    /**
     * Converts a Retrofit.Call to a CompletableFeature.
     * Used for complex async tasks like chaining.
     *
     * @param call
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> toCompletableFuture(Call<T> call) {
        CompletableFuture<T> future = new CompletableFuture<>();
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    future.complete(response.body());
                } else {
                    future.completeExceptionally(new IOException(
                            "API error: " + response.message()));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }
}
