package com.User.controllers;

import com.User.dto.ApiResponseMessage;
import com.User.dto.UserDto;
import com.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createNewUser(@RequestBody UserDto userDto){
        UserDto userDto1 = this.userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable String userId){
        UserDto userDto1 = this.userService.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUserById(@PathVariable String userId){
        this.userService.deleteUser(userId);
        ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder()
                .message("User has been deleted successfully")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    @GetMapping("/getSingleUser/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId){
        UserDto userDto = this.userService.getSingleUser(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDtos = this.userService.getAllUser();
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        UserDto userByEmail = this.userService.getUserByEmail(email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }


}
