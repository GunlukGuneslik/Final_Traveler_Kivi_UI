package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.PagedModel;
import com.example.actualtravellerkiviprojectui.dto.Post.PostDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface PostService {
    @GET("posts/feed")
    Call<PagedModel<PostDTO>> fetchFeed(@Query(value = "userId") int userId, @Query("page") int page, @Query("size") int size, @Query("sort") String sort);

    @GET("posts/feed")
    Call<PagedModel<PostDTO>> fetchAllPosts(@Query("page") int page, @Query("size") int size, @Query("sort") String sort);

    @GET("posts/{userId}")
    Call<List<PostDTO>> fetchUserPosts(@Path("userId") int userId);

    @POST("posts/updatetags/{postId}")
    Call<PostDTO> updatePostTags(@Path("postId") Integer postId, @Body List<String> tags);


    @GET("posts/{postId}")
    Call<PostDTO> fetchPost(@Path("postId") int postId);

    @GET("posts/{postId}/like")
    Call<PostDTO> likePost(@Path("postId") int postId, @Query("userId") int userId);

    @GET("posts/{postId}/unlike")
    Call<PostDTO> unlikePost(@Path("postId") int postId, @Query("userId") int userId);

    @GET("posts/{postId}/likers")
    Call<List<UserDTO>> likers(@Path("postId") int postId);

    @POST("posts/create")
    @Multipart
    Call<PostDTO> createPost(
            @Part("userId") int userId,
            @Part("body") String body,
            @Part("tags") List<String> tags,
            @Part MultipartBody.Part image
    );

    @GET("posts/{postId}/photo")
    @Streaming
    Call<ResponseBody> getPhoto(@Path("postId") int postId);

}
