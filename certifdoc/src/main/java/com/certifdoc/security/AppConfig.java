package com.certifdoc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AppConfig {
    // Déclare un bean PasswordEncoder que Spring pourra injecter partout où nécessaire
    // de la facon suivante
    // @Autowired
    // private PasswordEncoder passwordEncoder;
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Utilise BCrypt pour encoder les mots de passe
        // BCrypt est un algorithme de hachage robuste recommandé pour la sécurité des mots de passe
        return new BCryptPasswordEncoder(); // ✅ Fournit un encodeur sécurisé
}
}
