package com.example.security.service;


import com.example.security.jwt.JwtTokenProvider;
import com.example.security.repository.UserRepository;
import com.example.security.util.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public class BaseService {
    @Autowired protected UserRepository userRepository;
    @Autowired protected PasswordEncoder passwordEncoder;
    @Autowired protected AuthenticationManager authenticationManager;
    @Autowired protected JwtTokenProvider tokenProvider;
    @Autowired protected CacheManager cacheManager;

    protected String secretKey = "secrettoken";

    protected Integer getJwt(String accessToken) {
        if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")) {
            String jwt = accessToken.substring(7);
            return tokenProvider.getIdFromSubjectJWT(jwt);
        }
        return null;
    }
}
