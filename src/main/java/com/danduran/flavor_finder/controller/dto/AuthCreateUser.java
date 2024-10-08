package com.danduran.flavor_finder.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record AuthCreateUser(@NotBlank String username, @NotBlank String password, @NotBlank String email, @Valid AuthCreateRoleRequest roleRequest) {

}
