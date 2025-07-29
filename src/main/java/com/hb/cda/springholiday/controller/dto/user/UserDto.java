package com.hb.cda.springholiday.controller.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String id;
    private String email;
    private String role;
}
