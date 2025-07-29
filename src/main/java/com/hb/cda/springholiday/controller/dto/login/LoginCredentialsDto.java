package com.hb.cda.springholiday.controller.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginCredentialsDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
