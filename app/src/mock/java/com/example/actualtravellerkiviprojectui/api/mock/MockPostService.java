package com.example.actualtravellerkiviprojectui.api.mock;

import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.dto.PostCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.PostDTO;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockPostService implements PostService {
    private final BehaviorDelegate<PostService> delegate;

    public MockPostService(BehaviorDelegate<PostService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<List<PostDTO>> fetchFeed(int userId, int page, int size, String sort) {
        return null;
    }

    @Override
    public Call<PostDTO> fetchPost(int postId, int userId) {
        return null;
    }

    @Override
    public Call<PostDTO> likePost(int postId, int userId) {
        return null;
    }

    @Override
    public Call<PostDTO> unlikePost(int postId, int userId) {
        return null;
    }

    @Override
    public Call<List<UserDTO>> likers(int postId) {
        return null;
    }

    @Override
    public Call<PostDTO> createPost(PostCreateDTO post) {
        return null;
    }
}
