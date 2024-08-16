package com.danduran.flavor_finder.service;

import java.util.List;

import com.danduran.flavor_finder.model.User;

public interface UserService {
    User createUser(User user);
    User getUser(Long id);
    void deleteUser(Long id);
    List<User> getUsers();
} 
