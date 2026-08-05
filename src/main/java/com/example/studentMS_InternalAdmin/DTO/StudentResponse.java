package com.example.studentMS_InternalAdmin.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    private Long id;
    private String studentCode;
    private String username;
    private String fullName;
    private String gender;
    private LocalDate dob;
    private String phone;
    private String email;
    private String address;
    private String status;
    private ClassResponse className;
    private Set<SubjectResponse> subjects;
    private LocalDateTime createdAt;
}
