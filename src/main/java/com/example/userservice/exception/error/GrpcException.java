package com.example.userservice.exception.error;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class GrpcException extends StatusRuntimeException {
    public GrpcException(Status.Code code, String description) {
        super(Status.fromCode(code).withDescription(description));
    }
}