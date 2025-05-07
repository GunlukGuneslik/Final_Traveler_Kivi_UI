package com.example.actualtravellerkiviprojectui.api.mock;

import androidx.annotation.NonNull;

import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.dto.PagedModel;
import com.example.actualtravellerkiviprojectui.dto.PostCreateDTO;
import com.example.actualtravellerkiviprojectui.dto.PostDTO;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockPostService implements PostService {
    private final BehaviorDelegate<PostService> delegate;

    public MockPostService(BehaviorDelegate<PostService> delegate) {
        this.delegate = delegate;
    }

    @NonNull
    private  PostService getPostDetail() {
        return delegate.returningResponse(Utils.loadObject("mock/posts/postdetail.json", new TypeReference<PostDTO>() {
        }));
    }

    @Override
    public Call<PagedModel<PostDTO>> fetchFeed(int userId, int page, int size, String sort) {
        return delegate.returningResponse(Utils.loadObject("mock/posts/postfeeduser1.json", new TypeReference<PagedModel<PostDTO>>() {
        })).fetchFeed(userId, page, size, sort);
    }

    @Override
    public Call<PostDTO> fetchPost(int postId, int userId) {
        return getPostDetail().fetchPost(postId, userId);
    }

    @Override
    public Call<PostDTO> likePost(int postId, int userId) {
        return getPostDetail().likePost(postId, userId);
    }

    @Override
    public Call<PostDTO> unlikePost(int postId, int userId) {
        return getPostDetail().unlikePost(postId, userId);
    }

    @Override
    public Call<List<UserDTO>> likers(int postId) {
        return getPostDetail().likers(postId);
    }

    @Override
    public Call<PostDTO> createPost(PostCreateDTO post) {
        return getPostDetail().createPost(post);
    }
}
