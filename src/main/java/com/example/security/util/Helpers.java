package com.example.security.util;

import com.devskiller.friendly_id.FriendlyId;
import com.example.security.dto.SignUpRequestDTO;
import com.example.security.entity.User;
import com.example.security.exception.DataFormatException;
import com.example.security.exception.UserAlreadyExistsException;
import com.example.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class Helpers {

    public static void checkFormData(SignUpRequestDTO signUpRequestDTO) {
        if (!regexEmail(signUpRequestDTO.getEmail()))
            throw new DataFormatException(signUpRequestDTO.getEmail());
        if (!regexUsername(signUpRequestDTO.getUsername()))
            throw new DataFormatException(signUpRequestDTO.getUsername());
    }

    private static boolean regexUsername(String username) {
        if (username == null) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._]{6,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        return matcher.find();
    }

    private static boolean regexEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex =
                "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static String generateUid() {
        return FriendlyId.createFriendlyId();
    }
}
