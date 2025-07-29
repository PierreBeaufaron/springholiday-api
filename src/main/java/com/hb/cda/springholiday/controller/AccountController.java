package com.hb.cda.springholiday.controller;

import com.hb.cda.springholiday.business.AccountBusiness;
import com.hb.cda.springholiday.business.impl.AccountBusinessImpl;
import com.hb.cda.springholiday.controller.dto.UserMapper;
import com.hb.cda.springholiday.controller.dto.user.UserRegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hb.cda.springholiday.entity.User;
import com.hb.cda.springholiday.service.MailService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {
    private AccountBusinessImpl accountBusiness;
    private UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody UserRegisterDto dto) {
        accountBusiness.register(userMapper.convertToUser(dto));
        return "Check your email please";
    }

    @GetMapping("validate/{token}")
    public String activate(@PathVariable String token) {
        accountBusiness.activateUser(token);
        return "Account activated, you can now login";
    }


    
}
