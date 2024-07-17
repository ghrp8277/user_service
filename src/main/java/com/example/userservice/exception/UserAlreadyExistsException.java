package com.example.userservice.exception;

import com.example.userservice.exception.error.ErrorCodes;
import com.example.userservice.exception.error.ErrorMessages;
import com.example.userservice.exception.error.GrpcException;

public class UserAlreadyExistsException extends GrpcException {
    public UserAlreadyExistsException() {
        super(ErrorCodes.USER_ALREADY_EXISTS_CODE, ErrorMessages.USER_ALREADY_EXISTS);
    }
}