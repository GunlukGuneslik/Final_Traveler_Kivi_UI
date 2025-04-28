package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.PostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostService {
    public List<PostDTO> fetchPosts(int page, int size,String sort);

    public Call<PostDTO> createPost(@Body PostDTO post);

}
