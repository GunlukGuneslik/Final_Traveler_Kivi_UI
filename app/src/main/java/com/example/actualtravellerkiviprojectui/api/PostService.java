package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.PostCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.PostDTO;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostService {
    @GET("posts/feed/{userId}")
    Call<List<PostDTO>> fetchFeed(@Path("userId") int userId, @Query("page") int page, @Query("size") int size, @Query("sort") String sort);

    @GET("posts/{postId}/get")
    Call<PostDTO> fetchPost(@Path("postId") int postId, int userId);

    @GET("posts/{postId}/like")
    Call<PostDTO> likePost(@Path("postId") int postId, int userId);

    @GET("posts/{postId}/unlike")
    Call<PostDTO> unlikePost(@Path("postId") int postId, int userId);

    @GET("posts/{postId}/likers")
    Call<List<UserDTO>> likers(@Path("postId") int postId);

    @GET("posts/create")
    Call<PostDTO> createPost(@Body PostCreateDTO post);

}
