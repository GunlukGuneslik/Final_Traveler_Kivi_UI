package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.PostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostService {
    @GET("posts/feed")
    public Call<List<PostDTO>> fetchPosts(@Query("page") int page, @Query("size") int size, @Query("sort") String sort);

    @POST("posts/create")
    public Call<PostDTO> createPost(@Body PostDTO post);

}
