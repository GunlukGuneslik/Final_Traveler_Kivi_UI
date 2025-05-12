package com.example.actualtravellerkiviprojectui.api.mock;

import androidx.annotation.NonNull;

import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.PagedModel;
import com.example.actualtravellerkiviprojectui.dto.User.UserDTO;
import com.example.actualtravellerkiviprojectui.dto.User.UserStatsDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockUserService implements UserService {
    private final BehaviorDelegate<UserService> delegate;

    public MockUserService(BehaviorDelegate<UserService> delegate) {
        this.delegate = delegate;
    }

    @NonNull
    private UserService getUserResponse() {
        return delegate.returningResponse(Utils.loadObject("mock/users/userdetail.json", new TypeReference<UserDTO>() {
        }));
    }

    @Override
    public Call<UserDTO> getUser(Integer userId) {
        return getUserResponse().getUser(userId);
    }

    @Override
    public Call<UserDTO> getUserByEmail(String email) {
        return getUserResponse().getUserByEmail(email);
    }

    @Override
    public Call<List<UserDTO>> getAllUsers() {
        return delegate.returningResponse(Utils.loadObject("mock/users/users.json", new TypeReference<List<UserDTO>>() {
        })).getAllUsers();
    }

    @Override
    public Call<UserDTO> createUser(UserDTO userCreateDTO) {
        return getUserResponse().createUser(userCreateDTO);
    }


    @Override
    public Call<PagedModel<UserDTO>> getUsersByType(UserDTO.UserType userType) {
        return delegate.returningResponse(Utils.loadObject("mock/users/usertype.json", new TypeReference<PagedModel<UserDTO>>() {
        })).getUsersByType(userType);
    }

    @Override
    public Call<List<UserDTO>> getFollowersOfUser(int userId) {
        return delegate.returningResponse(Utils.loadObject("mock/users/users.json", new TypeReference<List<UserDTO>>() {
        })).getFollowersOfUser(userId);
    }

    @Override
    public Call<List<UserDTO>> getUserFollowing(int userId) {
        return delegate.returningResponse(Utils.loadObject("mock/users/users.json", new TypeReference<List<UserDTO>>() {
        })).getUserFollowing(userId);
    }

    @Override
    public Call<UserDTO> followUser(int userId, int targetUserId) {
        return getUserResponse().followUser(userId, targetUserId);
    }

    @Override
    public Call<UserDTO> unfollowUser(int userId, int targetUserId) {
        return getUserResponse().unfollowUser(userId, targetUserId);
    }

    /**
     * TODO: Use bytestream?
     * Returns the avatar of user.
     *
     * @param userId
     * @return
     */
    @Override
    public Call<ResponseBody> getAvatar(int userId) {
        byte[] bytes;
        try {
            bytes = Utils.loadMockJson("mock/users/default-profile.png").readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ResponseBody avatarBody = ResponseBody.create(MediaType.parse("image/png"), bytes);
        return delegate.returningResponse(avatarBody).getAvatar(userId);
    }

    @Override
    public Call<UserDTO> setAvatar(int userId, MultipartBody.Part image) {
        return getUserResponse().setAvatar(userId, image);
    }

    @Override
    public Call<UserStatsDTO> getUserStats(int userId) {
        return delegate.returningResponse(Utils.loadObject("mock/users/userstats.json", new TypeReference<UserStatsDTO>() {
        })).getUserStats(userId);
    }

    @Override
    public Call<Boolean> checkPassword(int userId, String password) {
        return delegate.returningResponse(true).checkPassword(userId, password);
    }

    @Override
    public Call<UserDTO> resetPassword(int userId) {
        return getUserResponse().resetPassword(userId);
    }
}
