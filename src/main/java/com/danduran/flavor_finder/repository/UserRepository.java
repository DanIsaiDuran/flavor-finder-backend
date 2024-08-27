package com.danduran.flavor_finder.repository;

import org.springframework.data.repository.CrudRepository;

import com.danduran.flavor_finder.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    
}