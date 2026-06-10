package com.cosodi.pos.service.impl;

import com.cosodi.pos.dto.LoginRequestDTO;
import com.cosodi.pos.dto.LoginResponseDTO;
import com.cosodi.pos.dto.RegisterRequest;
import com.cosodi.pos.dto.RegisterResponse;
import com.cosodi.pos.exception.ModelNotFoundException;
import com.cosodi.pos.entity.Role;
import com.cosodi.pos.entity.User;
import com.cosodi.pos.exception.UserAlreadyExistsException;
import com.cosodi.pos.repository.IRoleRepository;
import com.cosodi.pos.repository.IUserRepository;
import com.cosodi.pos.security.JwtService;
import com.cosodi.pos.service.IAuthService;
import com.cosodi.pos.util.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        Role defaultRole = roleRepository.findByName(RoleName.READ)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User newUser = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFirstName(),
                request.getLastName(),
                true, // enabled by default
                List.of(defaultRole)
        );

        userRepository.save(newUser);

        return RegisterResponse.success(request.getUsername(), request.getEmail());
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ModelNotFoundException(
                                "User not found: " + request.getUsername()
                        )
                );

        boolean validPassword = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!validPassword) {
            throw new RuntimeException("Invalid credentials");
        }

        String token =
                jwtService.generateToken(
                        user.getUsername()
                );

        return new LoginResponseDTO(
                user.getUsername(),
                token,
                "Login successful",
                true
        );
    }
} 