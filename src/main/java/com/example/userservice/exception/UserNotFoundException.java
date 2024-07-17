package com.example.userservice.exception;

import com.example.userservice.exception.error.ErrorCodes;
import com.example.userservice.exception.error.ErrorMessages;
import com.example.userservice.exception.error.GrpcException;

public class UserNotFoundException extends GrpcException {
    public UserNotFoundException() {
        super(ErrorCodes.USER_NOT_FOUND_CODE, ErrorMessages.USER_NOT_FOUND);
    }
}
