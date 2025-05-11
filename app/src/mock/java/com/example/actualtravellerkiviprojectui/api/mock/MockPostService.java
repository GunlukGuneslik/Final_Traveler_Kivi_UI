package com.example.actualtravellerkiviprojectui.api.mock;

import androidx.annotation.NonNull;

import com.example.actualtravellerkiviprojectui.api.PostService;
import com.example.actualtravellerkiviprojectui.dto.PagedModel;
import com.example.actualtravellerkiviprojectui.dto.Post.PostDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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
    public Call<PagedModel<PostDTO>> fetchAllPosts(int page, int size, String sort) {
        return null;
    }

    @Override
    public Call<List<PostDTO>> fetchUserPosts(int userId) {
        return null;
    }

    @Override
    public Call<PostDTO> updatePostTags(Integer postId, List<String> tags) {
        return getPostDetail().updatePostTags(postId, tags);
    }

    @Override
    public Call<PostDTO> fetchPost(int postId) {
        return getPostDetail().fetchPost(postId);
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
        return delegate.returningResponse(Utils.loadObject("mock/users/users.json", new TypeReference<List<UserDTO>>() {
        })).likers(postId);
    }

    @Override
    public Call<PostDTO> createPost(int userId, String body, List<String> tags, MultipartBody.Part image) {
        return getPostDetail().createPost(userId, body, tags, image);
    }

    @Override
    public Call<ResponseBody> getPhoto(int postId) {
        byte[] bytes;
        try {
            bytes = Utils.loadMockJson("mock/users/default-profile.png").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ResponseBody avatarBody = ResponseBody.create(MediaType.parse("image/png"), bytes);
        return delegate.returningResponse(avatarBody).getPhoto(postId);
    }

}
