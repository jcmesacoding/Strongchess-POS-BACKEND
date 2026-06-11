package com.cosodi.pos.service;

import com.cosodi.pos.dto.RegisterRequest;
import com.cosodi.pos.dto.UserResponseDTO;
import java.util.List;

public interface IUserService {
    List<UserResponseDTO> getAll();

    void delete(Long id);

    UserResponseDTO update(Long id, RegisterRequest request);
}
