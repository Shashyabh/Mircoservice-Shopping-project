package com.User.service.impl;

import com.User.dto.UserDto;
import com.User.entities.User;
import com.User.repositories.UserRepo;
import com.User.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setUserId(UUID.randomUUID().toString());
        User savedUser = this.userRepo.save(user);
        return this.modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not found with this Id"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());

        User savedUser = this.userRepo.save(user);
        
        return this.modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public void  deleteUser(String userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not found with this Id"));
        this.userRepo.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = this.userRepo.findAll();

        List<UserDto> userDtos = users.stream().map(user ->
                this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto getSingleUser(String userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User Not found with this Id"));
        return this.modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = this.userRepo.findUserByEmail(email);
        return modelMapper.map(user,UserDto.class);
    }
}
