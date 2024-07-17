package com.example.userservice.exception;

import com.example.userservice.exception.error.ErrorCodes;
import com.example.userservice.exception.error.ErrorMessages;
import com.example.userservice.exception.error.GrpcException;

public class IncorrectPasswordException extends GrpcException {
    public IncorrectPasswordException() {
        super(ErrorCodes.CURRENT_PASSWORD_INCORRECT_CODE, ErrorMessages.CURRENT_PASSWORD_INCORRECT);
    }
}
