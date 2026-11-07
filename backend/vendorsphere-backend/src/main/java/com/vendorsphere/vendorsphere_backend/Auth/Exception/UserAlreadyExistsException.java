package com.vendorsphere.vendorsphere_backend.Auth.Exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(
            String message
    ){
        super(message);
    }
}