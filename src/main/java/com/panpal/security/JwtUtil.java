package com.panpal.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.panpal.User.User;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

public class JwtUtil {
    public static User parseToken(String token) {

        DecodedJWT jwt = JWT.decode(token);
        System.out.println(jwt.getKeyId());

        JwkProvider provider = null;
        Jwk jwk = null;
        Algorithm algorithm = null;

        try {
            provider = new UrlJwkProvider(new URL("https://login.microsoftonline.com/common/discovery/keys"));
            jwk = provider.get(jwt.getKeyId());
            algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);
            User user = new User();
            user.setUsername(jwt.getClaim("unique_name").asString());
            return user;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JwkException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
