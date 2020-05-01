package com.diploma.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String accessToken;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

}
