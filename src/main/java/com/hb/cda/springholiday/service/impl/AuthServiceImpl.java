package com.hb.cda.springholiday.service.impl;

import com.hb.cda.springholiday.controller.dto.UserMapper;
import com.hb.cda.springholiday.controller.dto.login.LoginCredentialsDto;
import com.hb.cda.springholiday.controller.dto.login.LoginResponseDTO;
import com.hb.cda.springholiday.entity.RefreshToken;
import com.hb.cda.springholiday.entity.User;
import com.hb.cda.springholiday.repository.RefreshTokenRepository;
import com.hb.cda.springholiday.repository.UserRepository;
import com.hb.cda.springholiday.security.jwt.JwtUtil;
import com.hb.cda.springholiday.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserMapper userMapper;
    private final RefreshTokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public LoginResponseDTO login(LoginCredentialsDto credentials) {
        User user = (User)authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail(),
                        credentials.getPassword())
                ).getPrincipal();
        String token = jwtUtil.generateToken(user);
        return new LoginResponseDTO(token, userMapper.convertToUserDto(user));
    }

    @Override
    public String generateRefreshToken(String idUser) {
        RefreshToken refreshToken = new RefreshToken();
        User user = userRepository.findById(idUser).orElseThrow();
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(LocalDateTime.now().plus(30, ChronoUnit.DAYS));
        tokenRepository.save(refreshToken);
        return refreshToken.getId();
    }

    @Override
    public TokenPair validateRefreshToken(String token) {
        RefreshToken refreshToken = tokenRepository.findById(token).orElseThrow();
        if (refreshToken.isExpired()) {
            throw new RuntimeException("Refresh token expired");
        }

        User user = refreshToken.getUser();
        tokenRepository.delete(refreshToken);
        String newToken = generateRefreshToken(user.getId());
        String jwt = jwtUtil.generateToken(user);
        return new TokenPair(newToken, jwt);

    }

    @Transactional
    @Scheduled(fixedDelay = 24, timeUnit = TimeUnit.HOURS)
    void cleanExpiredTokens() {
        tokenRepository.deleteExpired();
    }


}
