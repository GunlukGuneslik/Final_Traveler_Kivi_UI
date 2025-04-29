package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.PostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface PostService {
    @GET("posts/feed")
    Call<List<PostDTO>> fetchPosts(int page, int size, String sort);

    @GET("posts/create")
    Call<PostDTO> createPost(@Body PostDTO post);

}
