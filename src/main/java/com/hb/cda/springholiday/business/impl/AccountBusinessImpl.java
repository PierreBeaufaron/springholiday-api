package com.hb.cda.springholiday.business.impl;

import com.hb.cda.springholiday.business.AccountBusiness;
import com.hb.cda.springholiday.business.exception.UserAlreadyExistsException;
import com.hb.cda.springholiday.entity.User;
import com.hb.cda.springholiday.repository.UserRepository;
import com.hb.cda.springholiday.security.jwt.JwtUtil;
import com.hb.cda.springholiday.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class AccountBusinessImpl implements AccountBusiness {
    private final MailService mailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setActive(false);
        userRepository.save(user);
        String token = jwtUtil.generateToken(user, Instant.now().plus(7, ChronoUnit.DAYS)); // On génère le JWT avec une durée de 7 jours
        mailService.sendEmailValidation(user, token); // On l'envoie par email

        return user;
    }

    @Override
    public void activateUser(String token) {

        User user = (User)jwtUtil.validateToken(token);
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String email) {

    }

    @Override
    public void deleteAccount(User user) {

    }
}
