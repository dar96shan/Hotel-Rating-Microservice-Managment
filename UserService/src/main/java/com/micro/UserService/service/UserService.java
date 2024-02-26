package com.micro.UserService.service;

import com.micro.UserService.entity.User;

import java.util.List;

public interface UserService {

    //Create
    User saveUser(User user);

    //get all user
    List<User> getAllUsers();

    //Get single user of given userId
    User getUser(String userId);
}
