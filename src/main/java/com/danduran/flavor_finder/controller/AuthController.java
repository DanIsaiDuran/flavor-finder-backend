package com.danduran.flavor_finder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danduran.flavor_finder.controller.dto.AuthCreateUser;
import com.danduran.flavor_finder.controller.dto.AuthLoginRequest;
import com.danduran.flavor_finder.controller.dto.AuthResponse;
import com.danduran.flavor_finder.service.UserDetailServiceImpl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    UserDetailServiceImpl userDetailService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUser authCreateUser) {
        
        return new ResponseEntity<>(this.userDetailService.createUser(authCreateUser), HttpStatus.CREATED);
    }
}
