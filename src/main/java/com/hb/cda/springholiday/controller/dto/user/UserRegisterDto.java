package com.hb.cda.springholiday.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 64)
    private String password;


}
