package com.diploma.backend.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
public class UserLoginDTO {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    @ToString.Exclude
    private String password;

}
