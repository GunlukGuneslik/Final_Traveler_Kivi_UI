package com.example.actualtravellerkiviprojectui.api.mock;

import com.example.actualtravellerkiviprojectui.api.UserService;
import com.example.actualtravellerkiviprojectui.dto.ImageDTO;
import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockUserService implements UserService {
    private final BehaviorDelegate<UserService> delegate;

    public MockUserService(BehaviorDelegate<UserService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<UserDTO> getUser(Integer userId) {
        return delegate.returningResponse("mock/users/userdetail");

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
        return null;
    }

    @Override
    public Call<UserDTO> followUser(int userId, int targetUserId) {
        return null;
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
