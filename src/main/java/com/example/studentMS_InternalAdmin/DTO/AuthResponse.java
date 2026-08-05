package com.example.studentMS_InternalAdmin.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;
    private String tokenType;
    private String username;
    private String role;

    public static AuthResponse of(String token, String username, String role) {
        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .username(username)
                .role(role)
                .build();
    }
}
