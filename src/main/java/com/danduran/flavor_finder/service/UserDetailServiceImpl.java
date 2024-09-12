package com.danduran.flavor_finder.service;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import com.danduran.flavor_finder.controller.dto.AuthLoginRequest;
import com.danduran.flavor_finder.controller.dto.AuthResponse;
import com.danduran.flavor_finder.model.UserEntity;
import com.danduran.flavor_finder.repository.UserRepository;
import com.danduran.flavor_finder.util.JwtUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    private JwtUtils jwtUtils;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


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

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String userName = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(userName, "User logged succesfully", token, true);
        return authResponse;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if(userDetails == null) throw new BadCredentialsException("Invalid username or password");

        if(!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("Invalid password");

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
