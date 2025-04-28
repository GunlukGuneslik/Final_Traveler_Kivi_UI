package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.PostDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class PostServiceMock implements PostService {
    //private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final List<PostDTO> postDTOList = objectMapper.readValue(,List.class);
    @Override
    public Call<List<PostDTO>> fetchPosts(int page, int size, String sort) {
        return null;
    }

    @Override
    public Call<PostDTO> createPost(PostDTO post) {
        return null;
    }
}
