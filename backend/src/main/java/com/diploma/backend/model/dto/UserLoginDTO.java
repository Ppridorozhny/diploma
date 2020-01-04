package com.diploma.backend.model.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginDTO {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    @ToString.Exclude
    private String password;
}
