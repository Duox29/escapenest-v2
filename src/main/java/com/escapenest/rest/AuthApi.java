package com.escapenest.rest;

import com.escapenest.model.request.*;
import lombok.RequiredArgsConstructor;
import com.escapenest.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> authentication(@RequestBody LoginRequest loginRequest){
        authService.login(loginRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        return new  ResponseEntity<>(authService.register(registerRequest), HttpStatus.CREATED);

    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody ChangeInformationUserRequest changeInformationUserRequest, @PathVariable Integer id){
        authService.updateUser(id,changeInformationUserRequest);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable Integer id){
        authService.changePassword(id,changePasswordRequest);
        return ResponseEntity.ok().build();

    }
    @PutMapping("/password-retrieval")
    public ResponseEntity<?> changePasswordForForgetPassword(@RequestBody UpsertPasswordRetrieval upsertPasswordRetrieval){
        authService.changePasswordForForgetPassword(upsertPasswordRetrieval);
        return ResponseEntity.ok().build();

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        authService.forgotPassword(email);
        return ResponseEntity.ok().build();
    }
}