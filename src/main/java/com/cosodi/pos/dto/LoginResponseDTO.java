package com.cosodi.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private String username;
    private String token;
    private String message;
    private boolean success;
}