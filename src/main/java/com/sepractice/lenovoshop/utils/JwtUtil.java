package com.sepractice.lenovoshop.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String secret = "cepractice-nbivelbqibdaksjdnask"; // JWT密钥

    private static final Integer expire = 24 * 60 * 60 * 1000; // 过期时间，单位为ms

    public static String getToken(String userId){
        return JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
                .sign(Algorithm.HMAC256(secret));
    }

    public static String parseToken(String token){
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("userId")
                .asString();
    }
}
