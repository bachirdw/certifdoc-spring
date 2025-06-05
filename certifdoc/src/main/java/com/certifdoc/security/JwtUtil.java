package com.certifdoc.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import com.certifdoc.entity.UserEntity;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;

// Composant Spring permettant de gérer les opérations liées aux JWT (génération, validation, extraction)
@Component
public class JwtUtil {

    // clé secrète pour signer le JWT
    private static final String SECRET_KEY = "my-super-secret-key-with-32-bytes!!!";

    //Génére une clé secrète à partir de la clé secrète en utilisant l'algorithme UTF-8
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
   /**
 * Je crée un JWT à partir d'un utilisateur de ton domaine (`UserEntity`).
 * Pour cela, je construis un `UserDetails` Spring compatible, puis j'appelle ta méthode existante `generateToken`.
 */
public String generateJwtToken(UserEntity user) {
    // Je construis un objet `UserDetails` à partir de ton entité UserEntity
    // Spring Security attend cet objet pour générer un token standard
    UserDetails userDetails = org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())  // Le `username` est ici l'email
        .password(user.getPassword())  // Obligatoire mais non utilisé dans le token
        .roles(user.getRole().name())  // Je convertis l'enum `RoleEntity` en String (ex: "ADMIN")
        .build();

    // Je réutilise la méthode existante qui génère un JWT depuis UserDetails
    return generateToken(userDetails);
}

    
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}