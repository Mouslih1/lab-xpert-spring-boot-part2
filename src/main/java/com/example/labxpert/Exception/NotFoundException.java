package com.example.labxpert.Exception;

import com.example.labxpert.Exception.common.ApiException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message)
    {
        super(message);
    }
}
