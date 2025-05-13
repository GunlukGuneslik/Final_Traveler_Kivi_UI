package com.example.actualtravellerkiviprojectui.api.modules;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.example.actualtravellerkiviprojectui.App;
import com.example.actualtravellerkiviprojectui.R;
import com.example.actualtravellerkiviprojectui.api.EventService;
import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * NetworkModule is only used in {@link com.example.actualtravellerkiviprojectui.api.ServiceLocator}
 */
public class NetworkModule {
    private static final String BASE_URL = App.getContext().getResources().getString(R.string.kivi_api_url);

    public static Retrofit provideRetrofit() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        //.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(JacksonConverterFactory.create(mapper)).build();
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

    /**
     * Sets a {@link ImageView} object's image to the result of a {@link Call} object.
     *
     * @param imageView
     * @param call      This is from the methods returning {@code  Call<ResponseBody>}.
     * @param callback  This is when you NEED the returned IMAGE. Contact Yusuf.
     */
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

    public static <T> void uploadImage(Context context, Uri imageUri, Function<MultipartBody.Part, Call<T>> apiCall, Consumer<T> onSuccess, Consumer<Throwable> onError) {

        try {
            // Properly convert Uri to actual file data
            File fileDir = context.getCacheDir();
            File file = new File(fileDir, UUID.randomUUID().toString() + ".png");

            Bitmap bitmap = null;
            ContentResolver contentResolver = context.getContentResolver();
            try {

                ImageDecoder.Source source = ImageDecoder.createSource(contentResolver, imageUri);
                bitmap = ImageDecoder.decodeBitmap(source);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (OutputStream outputStream = new FileOutputStream(file)) {
                if (bitmap == null) {
                    onError.accept(new IOException("Cannot read input file"));
                    return;
                }
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);

                outputStream.flush();
            }

            // Create request body for file
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);

            // Create MultipartBody.Part
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            // Execute the request
            Call<T> call = apiCall.apply(filePart);
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        onSuccess.accept(response.body());
                    } else {
                        onError.accept(new HttpException(response));
                    }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    onError.accept(t);
                }
            });

        } catch (Exception e) {
            onError.accept(e);
        }
    }
}
