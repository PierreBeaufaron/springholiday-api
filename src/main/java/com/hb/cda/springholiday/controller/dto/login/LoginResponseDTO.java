package com.hb.cda.springholiday.controller.dto.login;

import com.hb.cda.springholiday.controller.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private UserDto userDto;

}
