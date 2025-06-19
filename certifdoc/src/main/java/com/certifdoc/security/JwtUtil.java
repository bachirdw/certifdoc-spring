package com.certifdoc.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import com.certifdoc.entity.UserEntity;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

// Composant Spring permettant de gérer les opérations liées aux JWT (génération, validation, extraction)
@Component
public class JwtUtil {

    // Clé secrète pour signer les tokens, injectée depuis application.properties
    private final SecretKey secretKey;

    /**
     * Constructeur initialisant la clé secrète.
     * @param secret La clé encodée en Base64 depuis application.properties
     */
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        // Décodage de la clé Base64 et création de la SecretKey pour HS512
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * Génère un token JWT à partir des informations d'un UserDetails.
     * 
     * @param userDetails Les détails de l'utilisateur Spring Security
     * @return Le token JWT signé
     * 
     * Structure du token :
     * - Subject : email de l'utilisateur
     * - IssuedAt : date de création
     * - Expiration : 10 heures après création
     * - Signature : algorithme HS512
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // Email comme sujet
                .setIssuedAt(new Date()) // Date d'émission
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expiration dans 10h
                .signWith(secretKey, SignatureAlgorithm.HS512) // Signature avec notre clé
                .compact(); // Génération du token
    }

    /**
     * Génère un token JWT à partir d'une entité UserEntity.
     * Adapte l'entité métier au format attendu par Spring Security.
     * 
     * @param user L'entité UserEntity de l'application
     * @return Le token JWT signé
     */
    public String generateJwtToken(UserEntity user) {
        // Construction d'un UserDetails temporaire à partir de l'entité User
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()) // L'email comme identifiant
                .password(user.getPassword()) // Mot de passe (non utilisé après authentification)
                .roles(user.getRole().name()) // Conversion du rôle enum en String
                .build();

        return generateToken(userDetails); // Délégation à la méthode principale
    }

    /**
     * Extrait le username (email) d'un token JWT.
     * 
     * @param token Le token JWT à parser
     * @return L'email de l'utilisateur contenu dans le token
     * @throws io.jsonwebtoken.JwtException Si le token est invalide
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // Vérification avec notre clé
                .build()
                .parseClaimsJws(token) // Parsing du token
                .getBody()
                .getSubject(); // Extraction du sujet (email)
    }

    /**
     * Valide un token JWT contre un UserDetails.
     * 
     * @param token Le token à valider
     * @param userDetails Les détails de l'utilisateur à comparer
     * @return true si le token est valide et correspond à l'utilisateur
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        // Vérification que :
        // 1. Le username dans le token correspond à celui dans UserDetails
        // 2. Le token n'est pas expiré
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Vérifie si un token JWT est expiré.
     * 
     * @param token Le token à vérifier
     * @return true si le token est expiré, false sinon
     */
    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration() // Date d'expiration
                .before(new Date()); // Comparaison avec la date actuelle
    }
}
