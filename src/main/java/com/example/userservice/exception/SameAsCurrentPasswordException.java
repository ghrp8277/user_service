package com.example.userservice.exception;

import com.example.userservice.exception.error.ErrorCodes;
import com.example.userservice.exception.error.ErrorMessages;
import com.example.userservice.exception.error.GrpcException;

public class SameAsCurrentPasswordException extends GrpcException {
    public SameAsCurrentPasswordException() {
        super(ErrorCodes.SAME_AS_CURRENT_PASSWORD_CODE, ErrorMessages.SAME_AS_CURRENT_PASSWORD);
    }
}
