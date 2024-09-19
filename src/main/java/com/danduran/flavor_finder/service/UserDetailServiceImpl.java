package com.danduran.flavor_finder.service;


import org.hibernate.grammars.hql.HqlParser.SecondContext;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Simple;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import com.danduran.flavor_finder.controller.dto.AuthCreateUser;
import com.danduran.flavor_finder.controller.dto.AuthLoginRequest;
import com.danduran.flavor_finder.controller.dto.AuthResponse;
import com.danduran.flavor_finder.model.Role;
import com.danduran.flavor_finder.model.UserEntity;
import com.danduran.flavor_finder.repository.RoleRepository;
import com.danduran.flavor_finder.repository.UserRepository;
import com.danduran.flavor_finder.util.JwtUtils;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    
    private JwtUtils jwtUtils;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;


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

    public AuthResponse createUser(AuthCreateUser authCreateUser) {
        String username = authCreateUser.username();
        String email = authCreateUser.email();
        String password = authCreateUser.password();
        List<String> roles = authCreateUser.roleRequest().roleListName();
        Set<Role> rolesSet = roleRepository.findRoleByNameIn(roles).stream().collect(Collectors.toSet());

        UserEntity user = UserEntity.builder()
        .userName(username)
        .email(email)
        .password(passwordEncoder.encode(password))
        .roles(rolesSet)
        .isEnabled(true)
        .accountNoLocked(true)
        .accountNoExpired(true)
        .credentialNoExpired(true)
        .build();

        UserEntity createdUser = userRepository.save(user);

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        createdUser.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdUser.getUserName(), createdUser.getPassword(), authorityList);

        String token = jwtUtils.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(createdUser.getUserName(), "User created successfully", token, true);
        return authResponse;
    }
}
