package com.example.muruna.serviciousuarios.infrastructure.adapters.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtils {
    private static final String SECRET_KEY = "mySecretKey";
    private static final String ISSUER = "Muruna";
    public static boolean isJwtValid(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            // Si se necesitan mas validaciones adicionales van aqui.
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    public static String createJwtToken() {
        long expirationTime = 3600000; // 1 Hora
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        // Create and sign a JWT
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Error al generar Token: ", e );
            return null;
        }
    }
}
