package com.danduran.flavor_finder.service;

import java.util.List;

import com.danduran.flavor_finder.model.UserEntity;

public interface UserService {
    UserEntity createUser(UserEntity user);
    UserEntity getUser(Long id);
    UserEntity updateUser(Long id, UserEntity user);
    void deleteUser(Long id);
    List<UserEntity> getUsers();
} 
