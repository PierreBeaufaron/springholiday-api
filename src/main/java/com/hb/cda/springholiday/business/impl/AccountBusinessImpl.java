package com.hb.cda.springholiday.business.impl;

import com.hb.cda.springholiday.business.AccountBusiness;
import com.hb.cda.springholiday.business.exception.UserAlreadyExistsException;
import com.hb.cda.springholiday.entity.User;
import com.hb.cda.springholiday.repository.RefreshTokenRepository;
import com.hb.cda.springholiday.repository.UserRepository;
import com.hb.cda.springholiday.security.jwt.JwtUtil;
import com.hb.cda.springholiday.service.MailService;
import lombok.AllArgsConstructor;
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
    private final RefreshTokenRepository refreshTokenRepository;

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
        User user = userRepository.findByEmail(email)
                .orElseThrow();
        String token = jwtUtil.generateToken(user, Instant.now().plus(1, ChronoUnit.HOURS));
        mailService.sendResetPassword(user, token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        //Optionnel: On invalide tous les refresh token du user (on les supprime en fait)
        //pour le forcer à se reconnecter sur ses devices avec son nouveau mot de passe
        refreshTokenRepository.deleteByUser(user);

    }

    @Override
    public void deleteAccount(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAccount'");
    }

}
