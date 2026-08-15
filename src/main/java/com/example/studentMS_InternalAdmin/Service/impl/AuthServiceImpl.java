package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.AuthResponse;
import com.example.studentMS_InternalAdmin.DTO.LoginRequest;
import com.example.studentMS_InternalAdmin.Model.AdminModel;
import com.example.studentMS_InternalAdmin.Repository.AdminRepository;
import com.example.studentMS_InternalAdmin.Security.JwtTokenProvider;
import com.example.studentMS_InternalAdmin.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AdminRepository adminRepository;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, AdminRepository adminRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.adminRepository = adminRepository;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        AdminModel admin = adminRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Staff user not found: " + loginRequest.getUsername()));

        return AuthResponse.of(token, admin.getUsername(), admin.getRole());
    }
}
