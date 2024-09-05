package com.danduran.flavor_finder.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


import com.danduran.flavor_finder.model.UserEntity;
import com.danduran.flavor_finder.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("El usuario " + username  + " no existe"));
        
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        user.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));


        return new User(user.getUserName(), 
                user.getPassword(), 
                user.isEnabled(),
                user.isAccountNoExpired(),
                user.isCredentialNoExpired(),
                user.isAccountNoLocked(),
                authorityList
        );
    }
}
