package com.example.security.exception;

import com.example.security.dto.DataResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        DataResponseDTO dataResponse = new DataResponseDTO(e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),"");
        log.error(e.getMessage());
        return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        DataResponseDTO dataResponse = new DataResponseDTO(e.getMessage(),
                HttpStatus.NOT_FOUND.value(),"");
        log.error(e.getMessage());
        return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataFormatException.class)
    public ResponseEntity<?> handleDataFormatException(DataFormatException e) {
        DataResponseDTO dataResponse = new DataResponseDTO(e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),"");
        log.error(e.getMessage());
        return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
    }
}
