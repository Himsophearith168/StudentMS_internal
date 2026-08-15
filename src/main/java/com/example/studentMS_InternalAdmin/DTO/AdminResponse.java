package com.example.studentMS_InternalAdmin.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponse {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
}
