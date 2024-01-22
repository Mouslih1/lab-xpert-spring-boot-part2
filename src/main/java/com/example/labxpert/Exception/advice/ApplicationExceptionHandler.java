package com.example.labxpert.Exception.advice;

import com.example.labxpert.Exception.MessageErrorException.MessageError;
import com.example.labxpert.Exception.NotFoundException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return errorMap;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageError> hanldeBusinessException(NotFoundException exception)
    {
        MessageError messageError = new MessageError(exception.getMessage());
        return new ResponseEntity<>(messageError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<MessageError> hanldeValidationException(ValidationException exception)
    {
        MessageError messageError = new MessageError("Validation service error : " + exception.getMessage());
        return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageError> handleDataIntegrityViolationException(DataIntegrityViolationException exception)
    {
        MessageError messageError = new MessageError("Resource already exists : " + exception.getMessage());
        return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<MessageError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception)
    {
        MessageError messageError = new MessageError(exception.getMessage());
        return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
    }


   @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<MessageError> handleNumberFormatExceptionException(NumberFormatException exception)
    {
        MessageError messageError = new MessageError(exception.getMessage());
        return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageError> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception)
    {
        MessageError messageError = new MessageError(exception.getMessage());
        return new ResponseEntity<>(messageError, HttpStatus.BAD_REQUEST);
    }
}
