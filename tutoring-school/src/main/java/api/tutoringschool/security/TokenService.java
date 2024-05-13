package api.tutoringschool.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import api.tutoringschool.model.User;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secretKey;

    public String generateToken(User user) {
        try {
            var hmacAlgorithm = Algorithm.HMAC256(secretKey);

            String token = JWT.create()
                    .withIssuer(user.getId().toString())
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(hmacAlgorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating JWT token: ", exception);
        }
    }

    public String validateToken(String token) {
        try {
            var hmacAlgorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(hmacAlgorithm)
                    .withIssuer("tutoring-school")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
