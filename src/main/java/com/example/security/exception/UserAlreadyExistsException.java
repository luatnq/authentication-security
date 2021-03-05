package com.example.security.exception;

import com.example.security.entity.Message;
import com.example.security.entity.User;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super(Message.USER_ALREADY_EXISTS);
    }

    public UserAlreadyExistsException(User user) {
        super(Message.USER_ALREADY_EXISTS);
    }
}