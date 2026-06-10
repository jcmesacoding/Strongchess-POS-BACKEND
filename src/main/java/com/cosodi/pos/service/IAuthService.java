package com.cosodi.pos.service;

import com.cosodi.pos.dto.LoginRequestDTO;
import com.cosodi.pos.dto.LoginResponseDTO;
import com.cosodi.pos.dto.RegisterRequest;
import com.cosodi.pos.dto.RegisterResponse;
import jakarta.validation.Valid;

public interface IAuthService {
    
    RegisterResponse registerUser(RegisterRequest request);

    LoginResponseDTO login(@Valid LoginRequestDTO request);
} 