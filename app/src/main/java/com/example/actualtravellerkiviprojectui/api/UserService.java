package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.UserDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @GET("users/")
    Call<List<UserDTO>> getAllUsers();

    @POST("users/")
    Call<UserDTO> createUser(@Body UserCreateDTO userCreateDTO);


}
