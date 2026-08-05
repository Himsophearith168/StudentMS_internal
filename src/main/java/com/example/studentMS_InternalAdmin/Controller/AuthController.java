package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.AuthResponse;
import com.example.studentMS_InternalAdmin.DTO.LoginRequest;
import com.example.studentMS_InternalAdmin.Service.AuthService;
import com.example.studentMS_InternalAdmin.Util.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        APIResponse<AuthResponse> response = APIResponse.<AuthResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Login successful")
                .data(authResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
