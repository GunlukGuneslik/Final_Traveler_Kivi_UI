package com.example.actualtravellerkiviprojectui.api.modules;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.example.actualtravellerkiviprojectui.App;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.UserService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import okhttp3.ResponseBody;
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
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();
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

    public static void setImageViewFromCall(ImageView imageView, Call<ResponseBody> call, Consumer<ResponseBody> callback) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> r) {
                if (r.isSuccessful() && r.body() != null) {
                    try {
                        byte[] buf = r.body().bytes();
                        imageView.setImageBitmap(BitmapFactory.decodeByteArray(buf, 0, buf.length));
                        if (callback != null) {
                            callback.accept(r.body());
                        }
                    } catch (IOException e) {
                        Log.w("profilePhoto", e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.w("profilePhoto", t.getMessage());
            }
        });
    }
}
