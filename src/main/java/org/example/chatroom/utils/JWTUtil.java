package org.example.chatroom.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.example.chatroom.dto.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {
    public static String GenTokens(User user) {
        Map<String, Object> claim = new HashMap<>();
        claim.put("id", user.getId());
        claim.put("username", user.getUsername());
        return JWT.create()
                .withClaim("user", claim)
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .sign(Algorithm.HMAC256("secret"));
    }

    public static Map<String, Object> ParseTokens(String token) {
        return JWT.require(Algorithm.HMAC256("secret"))
                .build()
                .verify(token)
                .getClaim("user")
                .asMap();
    }
}
