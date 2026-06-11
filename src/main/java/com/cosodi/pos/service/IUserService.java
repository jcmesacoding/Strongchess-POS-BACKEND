package com.cosodi.pos.service;

import com.cosodi.pos.dto.UserResponseDTO;
import java.util.List;

public interface IUserService {
    List<UserResponseDTO> getAll();
}