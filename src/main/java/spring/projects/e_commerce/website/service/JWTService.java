package spring.projects.e_commerce.website.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.projects.e_commerce.website.entity.Customer;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiryInSeconds}")
    private int expiryInSeconds;
    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(Customer customer) {
        return JWT.create()
                .withClaim("USERNAME", customer.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +  expiryInSeconds))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String extractUsername(String token) {
        return JWT.decode(token)
                .getClaim("USERNAME").asString();
    }
}
