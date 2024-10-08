package com.danduran.flavor_finder.service;

import java.util.List;

import com.danduran.flavor_finder.exception.UserNotFoundException;
import com.danduran.flavor_finder.model.UserEntity;

public interface UserService {
    UserEntity createUser(UserEntity user);
    UserEntity getUser(Long id) throws UserNotFoundException;
    UserEntity updateUser(Long id, UserEntity user) throws UserNotFoundException;
    void deleteUser(Long id);
    List<UserEntity> getUsers();
} 
