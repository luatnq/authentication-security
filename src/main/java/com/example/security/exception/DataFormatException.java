package com.example.security.exception;


import com.example.security.entity.Message;

public class DataFormatException extends RuntimeException{

    public DataFormatException(String str){
        super(str+" "+ Message.DATA_FORMAT_EXCEPTION);
    }
}
