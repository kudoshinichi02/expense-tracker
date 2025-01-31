package com.example.expensetracker.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserException extends Exception {
    public UserException(String message) {
        super(message);
    }
}
