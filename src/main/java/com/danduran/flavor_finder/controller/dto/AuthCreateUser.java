package com.danduran.flavor_finder.controller.dto;

import com.danduran.flavor_finder.validation.UniqueEmail;
import com.danduran.flavor_finder.validation.UniqueUsername;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUser(@NotBlank @UniqueUsername String username, @NotBlank String password, @NotBlank @UniqueEmail String email, @Valid AuthCreateRoleRequest roleRequest) {

}
