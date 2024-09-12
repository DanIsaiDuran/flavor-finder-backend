package com.danduran.flavor_finder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.danduran.flavor_finder.model.UserEntity;
import com.danduran.flavor_finder.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository UserRepository;
    //private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserEntity createUser(UserEntity UserEntity) {
        //UserEntity.setPassword(bCryptPasswordEncoder.encode(UserEntity.getPassword()));
        return UserRepository.save(UserEntity);
    }

    @Override
    public UserEntity getUser(Long id) {
        Optional<UserEntity> UserEntity = UserRepository.findById(id);
        return unwrapUserEntity(UserEntity);
    }

    @Override
    public void deleteUser(Long id) {
        UserRepository.deleteById(id);
    }

    @Override
    public UserEntity updateUser(Long id, UserEntity user) {
        UserEntity oldUser = unwrapUserEntity(UserRepository.findById(id));
        oldUser.setUserName(user.getUserName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        return UserRepository.save(oldUser);
    }

    @Override
    public List<UserEntity> getUsers() {
        return (List<UserEntity>)UserRepository.findAll();
    }


    private UserEntity unwrapUserEntity(Optional<UserEntity> entity){
        if (entity.isPresent()) return entity.get();
        else throw new RuntimeException("Usuario no encontrado");
    }
}
