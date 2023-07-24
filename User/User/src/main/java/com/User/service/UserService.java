package com.User.service;

import com.User.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto,String userId);

    void deleteUser(String userId);

    List<UserDto> getAllUser();

    UserDto getSingleUser(String userId);

    UserDto getUserByEmail(String email);

}
