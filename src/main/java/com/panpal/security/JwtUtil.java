package com.panpal.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.panpal.User;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String secret = "this is a secret";

    public static String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        try{
            return JWT.create().withClaim("id", user.getId()).withClaim("name",user.getName())
                    .sign(Algorithm.HMAC256(secret));
        }catch (Exception e){
            return  null;
        }
    }

    public static User parseToken(String token) {
//        try {
//            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret))
//                    .build();
//            DecodedJWT decodeToken = jwtVerifier.verify(token);
//            User user = new User();
//            user.setId(decodeToken.getClaim("id").asInt());
//            user.setName(decodeToken.getClaim("name").asString());
//            return user;
//        }
//        catch (UnsupportedEncodingException e) {
//            return null;
//        }
        return null;
    }
}
