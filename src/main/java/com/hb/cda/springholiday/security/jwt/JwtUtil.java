package com.hb.cda.springholiday.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hb.cda.springholiday.security.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class JwtUtil {
    private UserService userService;
    private KeyManager keyManager;

    /**
     * Méthode avec durée de vie du token par défaut
     * @param user
     * @return
     */
    public String generateToken(UserDetails user) {
        return  generateToken(user, Instant.now().plus(30, ChronoUnit.MINUTES));
    }

    /**
     * Cette méthode génère un JWT contenant l'identifiant du user passé en paramètre
     * @param user (UserDetails) Le User pour lequel on souhaite créer un JWT
     * @param expiration Attend une durée de vie (pour le register par exemple)
     * @return le JWT généré
     */
    public String generateToken(UserDetails user, Instant expiration) {

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(expiration)
                .sign(keyManager.getAlgorithm());
    }

    /**
     * Cette méthode vérifie que le Token est bien généré par nous et récupère le user associé
     * @param token en chaine de caractères
     * @return le User lié au token
     */
    public UserDetails validateToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT
                    .require(keyManager.getAlgorithm())
                    .build()
                    .verify(token);
            String userIdentifier = decodedJWT.getSubject();
            return userService.loadUserByUsername(userIdentifier);
        } catch (JWTVerificationException | UsernameNotFoundException e) {
            throw new AuthorizationDeniedException("Error verifying token");
        }
    }


}
