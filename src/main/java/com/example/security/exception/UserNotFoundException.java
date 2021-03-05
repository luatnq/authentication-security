package com.example.security.exception;


import com.example.security.entity.Message;
import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super(Message.USER_NOT_FOUND_EXCEPTION);
    }

}
