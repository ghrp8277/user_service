package com.example.userservice.exception;

import com.example.userservice.exception.error.ErrorCodes;
import com.example.userservice.exception.error.ErrorMessages;
import com.example.userservice.exception.error.GrpcException;

public class EmailAlreadyExistsException extends GrpcException {
    public EmailAlreadyExistsException() {
        super(ErrorCodes.EMAIL_ALREADY_EXISTS_CODE, ErrorMessages.EMAIL_ALREADY_EXISTS);
    }
}
