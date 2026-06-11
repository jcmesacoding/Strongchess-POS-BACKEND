package com.cosodi.pos.service.impl;

import com.cosodi.pos.dto.RegisterRequest;
import com.cosodi.pos.dto.UserResponseDTO;
import com.cosodi.pos.entity.Role;
import com.cosodi.pos.entity.User;
import com.cosodi.pos.repository.IRoleRepository;
import com.cosodi.pos.repository.IUserRepository;
import com.cosodi.pos.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDTO> getAll() {
        return iUserRepository.findAll().stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.isEnabled(),
                        user.getRegistrationDate(),
                        user.getRoles().stream()
                                .map(role -> role.getName().name())
                                .toList()
                ))
                .toList();
    }

    @Override
    public UserResponseDTO update(Long id, RegisterRequest request) {
        User user = iUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRole() != null) {
            Role role = roleRepository.findByName(request.getRole())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRoles(List.of(role));
        }

        iUserRepository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isEnabled(),
                user.getRegistrationDate(),
                user.getRoles().stream().map(r -> r.getName().name()).toList()
        );
    }

    @Override
    public void delete(Long id) {
        iUserRepository.deleteById(id);
    }
}
