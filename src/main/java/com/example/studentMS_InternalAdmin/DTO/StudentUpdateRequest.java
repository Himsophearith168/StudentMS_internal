package com.example.studentMS_InternalAdmin.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentUpdateRequest {

    private String studentCode;

    private String username;

    private String password;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String gender;

    private LocalDate dob;

    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    private String address;

    private String status;

    private Long classId;

    private Set<Long> subjectIds;
}

