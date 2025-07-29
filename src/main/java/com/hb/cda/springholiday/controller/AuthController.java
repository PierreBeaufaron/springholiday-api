package com.hb.cda.springholiday.controller;

import com.hb.cda.springholiday.controller.dto.login.LoginCredentialsDto;
import com.hb.cda.springholiday.controller.dto.login.LoginResponseDTO;
import com.hb.cda.springholiday.entity.User;
import com.hb.cda.springholiday.service.impl.AuthServiceImpl;
import com.hb.cda.springholiday.service.impl.TokenPair;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginCredentialsDto credentials) {
        LoginResponseDTO responseDTO = authService.login(credentials);
        String refreshToken = authService.generateRefreshToken(responseDTO.getUserDto().getId());
        ResponseCookie refreshCookie = generateCookie(refreshToken);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(responseDTO);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<String> refreshToken(@CookieValue(name = "refresh-token") String token) {
        try {

            TokenPair tokens = authService.validateRefreshToken(token);
            ResponseCookie refreshCookie = generateCookie(tokens.getRefreshToken());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body(tokens.getJwt());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token");
        }

    }


    @GetMapping("protected")
    public String protec(@AuthenticationPrincipal User user) {
        System.out.println("hola");
        return user.getEmail();
    }

    private ResponseCookie generateCookie(String refreshToken) {
        ResponseCookie refreshCookie = ResponseCookie.from("refresh-token", refreshToken)
                .httpOnly(true)
                .secure(false)//faudrait plutôt mettre à true pour qu'il ne soit envoyé qu'en HTTPS, mais le temps du dev on le met à false
                .sameSite(SameSiteCookies.NONE.toString())
                .path("/api/refresh-token")
                .build()
                ;
        return refreshCookie;
    }


}
