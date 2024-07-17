package com.example.userservice.annotation;

import com.example.userservice.util.GrpcResponseHelper;
import com.example.grpc.*;
import io.grpc.stub.StreamObserver;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GrpcExceptionHandlingAspect {

    @Autowired
    private GrpcResponseHelper grpcResponseHelper;

    @Around("@annotation(com.example.userservice.annotation.GrpcExceptionHandler)")
    public void handleGrpcExceptions(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        @SuppressWarnings("unchecked")
        StreamObserver<Response> responseObserver = (StreamObserver<Response>) args[args.length - 1];

        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            grpcResponseHelper.sendErrorResponse(e, responseObserver);
        }
    }
}