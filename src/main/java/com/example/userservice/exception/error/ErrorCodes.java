package com.example.userservice.exception.error;

import io.grpc.Status;

public class ErrorCodes {
    public static final Status.Code USER_NOT_FOUND_CODE = Status.Code.NOT_FOUND;
    public static final Status.Code USER_ALREADY_EXISTS_CODE = Status.Code.ALREADY_EXISTS;
    public static final Status.Code AUTHENTICATION_FAILED_CODE = Status.Code.UNAUTHENTICATED;
    public static final Status.Code CURRENT_PASSWORD_INCORRECT_CODE = Status.Code.INVALID_ARGUMENT;
    public static final Status.Code EMAIL_ALREADY_EXISTS_CODE = Status.Code.ALREADY_EXISTS;
    public static final Status.Code SAME_AS_CURRENT_PASSWORD_CODE = Status.Code.INVALID_ARGUMENT;
}