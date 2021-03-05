package com.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataResponseDTO {
    public Object timestamp;
    public Object message;
    public Object statusCode;
    public Object error;

    public DataResponseDTO(Object message, Object statusCode, Object error){
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.statusCode = statusCode;
        this.error = error;
    }
}
