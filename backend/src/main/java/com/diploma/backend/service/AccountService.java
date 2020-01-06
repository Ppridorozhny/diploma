package com.diploma.backend.service;

import com.diploma.backend.model.dto.JwtAuthenticationResponse;
import com.diploma.backend.model.dto.UserLoginDTO;

public interface AccountService {
    JwtAuthenticationResponse login(UserLoginDTO loginDTO);
}
