package com.example.userservice.grpc;

import com.example.grpc.UserServiceGrpc;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import com.example.grpc.*;
import com.example.userservice.util.GrpcResponseHelper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    @Autowired
    private UserService userService;

    @Autowired
    private GrpcResponseHelper grpcResponseHelper;

    @Override
    public void registerUser(RegisterUserRequest request, StreamObserver<Response> responseObserver) {
        User newUser = userService.registerUser(request);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", newUser.getId());
        responseData.put("username", newUser.getUsername());
        grpcResponseHelper.sendJsonResponse("user", responseData, responseObserver);
    }

    @Override
    public void checkUsername(CheckUsernameRequest request, StreamObserver<Response> responseObserver) {
        boolean isDuplicate = userService.checkUsername(request.getUsername());
        grpcResponseHelper.sendJsonResponse("isDuplicate", isDuplicate, responseObserver);
    }

    @Override
    public void authenticateUser(AuthenticateUserRequest request, StreamObserver<Response> responseObserver) {
        Map<String, Object> responseData = userService.authenticateUser(request.getUsername(), request.getPassword());
        grpcResponseHelper.sendJsonResponse("result", responseData, responseObserver);
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request, StreamObserver<Response> responseObserver) {
        Optional<String> password = userService.updatePassword(request.getUserId(), request.getCurrentPassword(), request.getNewPassword());
        grpcResponseHelper.sendJsonResponse("password", password, responseObserver);
    }

    @Override
    public void findUser(FindUserRequest request, StreamObserver<Response> responseObserver) {
        User user = userService.findUserByUserId(request.getUserId());

        if (user == null) {
            grpcResponseHelper.sendErrorResponse("User not found", responseObserver);
            return;
        }

        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("username", user.getUsername());
        userResponse.put("password", user.getPassword());

        grpcResponseHelper.sendJsonResponse("user", userResponse, responseObserver);
    }

    @Override
    public void updateProfile(UpdateProfileRequest request, StreamObserver<Response> responseObserver) {
        User user = userService.updateProfile(request);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("id", user.getId());
        responseData.put("username", user.getUsername());
        grpcResponseHelper.sendJsonResponse("user", responseData, responseObserver);
    }
}
