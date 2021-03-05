package com.example.security.service.impl;

import com.example.security.entity.Message;
import com.example.security.exception.DataFormatException;
import com.example.security.exception.UserAlreadyExistsException;
import com.example.security.exception.UserNotFoundException;
import com.example.security.util.Helpers;
import com.example.security.dto.LoginRequestDTO;
import com.example.security.dto.LoginResponseDTO;
import com.example.security.dto.SignUpRequestDTO;
import com.example.security.dto.UserDTO;
import com.example.security.entity.CustomUserDetails;
import com.example.security.entity.User;
import com.example.security.entity.types.UserRole;
import com.example.security.mapper.UserMapper;
import com.example.security.service.BaseService;
import com.example.security.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Log4j2
@Service
@Transactional
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

    @Override
    public UserDTO register(SignUpRequestDTO signUpRequest) {
        checkUserExists(signUpRequest.getUsername());
        Helpers.checkFormData(signUpRequest);
        User user = User.builder()
                .uid(Helpers.generateUid())
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encodePassword(signUpRequest))
                .userRole(UserRole.ROLE_USER)
                .build();

        user = userRepository.save(user);
        return UserMapper.convertToDTO(user);
    }

    @Override
    @CachePut(value = "tokenCache", key = "#result.refreshToken")
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        User user = findByUsername(loginRequest.getUsername());
        return getLoginResponse(loginRequest, user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = findByUsername(username);
        return CustomUserDetails.create(user);
    }

    @Override
    public UserDetails loadUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new UsernameNotFoundException(Message.USER_NOT_FOUND_EXCEPTION));
        return CustomUserDetails.create(user);
    }

    @Override
    public UserDTO findProfileUserById(String accessToken) {
        User user = getUserByToken(accessToken);
        return UserMapper.convertToDTO(user);
    }

    @Override
    @Cacheable(value = "tokenCache", key = "#refreshToken")
    public LoginResponseDTO loadAccessTokenByRefreshToken(String refreshToken) {
        User user = getUserByToken(refreshToken);
        return new LoginResponseDTO(tokenProvider.generateAccessToken(user), "");
    }

    private String encodePassword(SignUpRequestDTO signUpRequest) {
        return passwordEncoder.encode(signUpRequest.getPassword());
    }

    private User getUserByToken(String token) {
        int id = getJwt(token);
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(Message.USER_NOT_FOUND_EXCEPTION));
        return user;
    }

    private void checkUserExists(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (Objects.nonNull(user)) throw new UserAlreadyExistsException();
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());
    }


    private LoginResponseDTO getLoginResponse(LoginRequestDTO dto, User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenProvider.generateAccessToken(user);
            String refreshToken = tokenProvider.generateRefreshToken(user);
            return new LoginResponseDTO(accessToken, refreshToken);
        } catch (Exception e) {
            log.debug(e);
        }
        return null;
    }
}

