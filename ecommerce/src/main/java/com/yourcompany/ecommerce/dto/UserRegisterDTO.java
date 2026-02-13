package com.yourcompany.ecommerce.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 6)
    private String password;
}
