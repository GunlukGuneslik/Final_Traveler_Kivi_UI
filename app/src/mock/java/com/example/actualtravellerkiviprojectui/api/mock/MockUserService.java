package com.example.actualtravellerkiviprojectui.api.mock;

import androidx.annotation.NonNull;

import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.ImageDTO;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

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
    public Call<List<UserDTO>> getAllUsers() {
        return null;
    }

    @Override
    public Call<UserDTO> createUser(UserDTO userCreateDTO) {
        return null;
    }

    @Override
    public Call<ImageDTO> getAvatarOfUser(int userId) {
        return null;
    }

    @Override
    public Call<List<UserDTO>> getUsersByType(UserDTO.UserType userType) {
        return null;
    }

    @Override
    public Call<List<UserDTO>> getFollowersOfUser(int userId) {
        return null;
    }

    @Override
    public Call<List<UserDTO>> getUserFollowed(int userId) {
        return delegate.returningResponse(Utils.loadObject("mock/users/users.json", new TypeReference<List<UserDTO>>() {
        })).getUserFollowed(userId);
    }

    @Override
    public Call<UserDTO> followUser(int userId, int targetUserId) {
        return getUserResponse().followUser(userId, targetUserId);
    }

    /**
     * TODO: Use bytestream?
     * Returns the avatar of user.
     *
     * @param userId
     * @return
     */
    @Override
    public Call<String> getAvatar(int userId) {
        return null;
    }
}
