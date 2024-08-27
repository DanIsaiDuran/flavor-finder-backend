package com.danduran.flavor_finder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.danduran.flavor_finder.model.User;
import com.danduran.flavor_finder.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    //private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User createUser(User user) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        User oldUser = unwrapUser(userRepository.findById(id));
        oldUser.setUserName(user.getUserName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        return userRepository.save(oldUser);
    }

    @Override
    public List<User> getUsers() {
        return (List<User>)userRepository.findAll();
    }


    private User unwrapUser(Optional<User> entity){
        if (entity.isPresent()) return entity.get();
        else throw new RuntimeException("Usuario no encontrado");
    }
}
