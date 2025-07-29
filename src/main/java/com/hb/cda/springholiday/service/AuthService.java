package com.hb.cda.springholiday.service;

import com.hb.cda.springholiday.controller.dto.login.LoginCredentialsDto;
import com.hb.cda.springholiday.controller.dto.login.LoginResponseDTO;
import com.hb.cda.springholiday.service.impl.TokenPair;

public interface AuthService {

    /**
     * Méthode qui va utiliser le AuthenticationManager pour récupérer le UserDetails
     * et créer un JWT de celui-ci
     * @param credentials Les infos de connexion du user (email + passwrd)
     * @return le token et le user sous forme UserDto
     */
    LoginResponseDTO login(LoginCredentialsDto credentials);

    /**
     * Méthode qui va créer un nouveau refresh token et le faire persister en database
     * @param idUser L'id du user pour lequel on souhaite générer un refresh token
     * @return L'id du token généré
     */

    String generateRefreshToken(String idUser);

    /**
     * Méthode qui va vérifier qu'un token existe en bdd, qu'il n'est pas expiré et s'il est ok,
     * génère un JWT, regénère un refresh token et supprimer l'ancien refresh token
     * @param token Le refresh token à valider
     * @return Les nouveaux refresh et jwt token
     */
    TokenPair validateRefreshToken(String token);
}
