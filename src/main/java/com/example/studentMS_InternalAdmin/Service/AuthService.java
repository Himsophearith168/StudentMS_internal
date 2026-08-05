package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.AuthResponse;
import com.example.studentMS_InternalAdmin.DTO.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
}
