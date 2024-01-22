package com.example.labxpert.Exception;

public class EmailDuplicateRecordException extends RuntimeException {
    public EmailDuplicateRecordException(String message){
        super(message);
    }
}
