package com.cosodi.pos.service.impl;

import com.cosodi.pos.dto.UserResponseDTO;
import com.cosodi.pos.repository.IUserRepository;
import com.cosodi.pos.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository iUserRepository;

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
}