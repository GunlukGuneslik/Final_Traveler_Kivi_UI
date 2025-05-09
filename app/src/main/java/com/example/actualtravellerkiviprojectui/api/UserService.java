package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.ImageDTO;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @GET("users/{userId}/get")
    Call<UserDTO> getUser(@Path("userId") Integer userId);

    @GET("users/")
    Call<List<UserDTO>> getAllUsers();

    @POST("users/")
    Call<UserDTO> createUser(@Body UserDTO userCreateDTO);

    @GET("users/{userId}/avatar")
    Call<ImageDTO> getAvatarOfUser(@Path("userId") int userId);

    @GET("users/type/{userType}")
    Call<List<UserDTO>> getUsersByType(@Path("userType") UserDTO.UserType userType);

    @GET("users/{userId}/followers")
    Call<List<UserDTO>> getFollowersOfUser(@Path("userId") int userId);

    @GET("users/{userId}/followed")
    Call<List<UserDTO>> getUserFollowed(@Path("userId") int userId);

    @GET("users/{userId}/follow")
    Call<UserDTO> followUser(@Path("userId") int userId, @Query("targetUserId") int targetUserId);

    /**
     * TODO: Use bytestream?
     * Returns the avatar of user.
     *
     * @param userId
     * @return
     */
    @GET("users/{userId}/avatar")
    // important for large downloads
    Call<ResponseBody> getAvatar(@Path("userId") int userId);

}
