package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.ImageDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserStatsDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface UserService {
    @GET("users/{userId}")
    Call<UserDTO> getUser(@Path("userId") Integer userId);

    @GET("users")
    Call<List<UserDTO>> getAllUsers();

    @POST("users")
    Call<UserDTO> createUser(@Body UserDTO userCreateDTO);

    @GET("users/{userId}/avatar")
    Call<ImageDTO> getAvatarOfUser(@Path("userId") int userId);

    @GET("users/type/{userType}")
    Call<List<UserDTO>> getUsersByType(@Path("userType") UserDTO.UserType userType);

    @GET("users/{userId}/followers")
    Call<List<UserDTO>> getFollowersOfUser(@Path("userId") int userId);

    @GET("users/{userId}/following")
    Call<List<UserDTO>> getUserFollowing(@Path("userId") int userId);

    @POST("users/{userId}/follow")
    Call<UserDTO> followUser(@Path("userId") int userId, @Query("targetUserId") int targetUserId);

    @POST("users/{userId}/follow")
    Call<UserDTO> unfollowUser(@Path("userId") int userId, @Query("targetUserId") int targetUserId);

    /**
     * TODO: Use bytestream?
     * Returns the avatar of user.
     *
     * @param userId
     * @return
     */
    @GET("users/{userId}/avatar")
    @Streaming
    // important for large downloads
    Call<ResponseBody> getAvatar(@Path("userId") int userId);

    @POST("users/{userId}/avatar")
    @Multipart
    Call<ResponseBody> setAvatar(@Path("userId") int userId, @Part("file") MultipartBody.Part image );

    @GET("users/{userId}/stats")
    Call<UserStatsDTO> getUserStats(@Path("userId") int userId);
    @GET("users/{userId}/checkPassword")
    Call<UserStatsDTO> getUserStats(@Path("userId") int userId, @Query("password") String password);
}
