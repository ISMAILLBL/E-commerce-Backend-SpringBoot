package com.yourcompany.ecommerce.dto;

import lombok.Getter;

@Getter
public class JwtResponseDTO {
    private final String token;
    private final String tokenType = "Bearer";

    public JwtResponseDTO(String token) {
        this.token = token;
    }
}
