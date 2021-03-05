package com.example.security.controller;


import com.example.security.dto.LoginRequestDTO;
import com.example.security.dto.LoginResponseDTO;
import com.example.security.dto.SignUpRequestDTO;
import com.example.security.dto.UserDTO;
import com.example.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class AuthenticationController {

    @Autowired
    protected UserService userService;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequest) {
        UserDTO userDTO = userService.register(signUpRequest);
        log.info("{}", userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO loginResponseDTO = userService.login(loginRequest);
        log.info("Login successful");
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @GetMapping("user/profile")
    public ResponseEntity<?> getProfileUserById(@RequestHeader("Authorization") String header) {
        UserDTO userDTO = userService.findProfileUserById(header);
        log.info("", userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<?> getAccessTokenByRefreshToken(@RequestHeader("Authorization") String header) {
        LoginResponseDTO loginResponse = userService.loadAccessTokenByRefreshToken(header);
        log.info("Reload accessToken successful");
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
