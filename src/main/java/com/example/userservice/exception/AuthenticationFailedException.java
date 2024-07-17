package com.example.userservice.exception;

import com.example.userservice.exception.error.ErrorCodes;
import com.example.userservice.exception.error.ErrorMessages;
import com.example.userservice.exception.error.GrpcException;

public class AuthenticationFailedException extends GrpcException {
    public AuthenticationFailedException() {
        super(ErrorCodes.AUTHENTICATION_FAILED_CODE, ErrorMessages.AUTHENTICATION_FAILED);
    }
}
