package com.danduran.flavor_finder.repository;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import com.danduran.flavor_finder.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByUserName(String username);
}