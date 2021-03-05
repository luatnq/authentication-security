package com.example.security.service;


import com.example.security.dto.LoginRequestDTO;
import com.example.security.dto.LoginResponseDTO;
import com.example.security.dto.SignUpRequestDTO;
import com.example.security.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTO register(SignUpRequestDTO signUpRequest);

    LoginResponseDTO login(LoginRequestDTO loginRequest);

    UserDetails loadUserByUsername(String username);

    UserDetails loadUserById(int id);

    UserDTO findProfileUserById(String accessToken);

    LoginResponseDTO loadAccessTokenByRefreshToken(String refreshToken);
}
