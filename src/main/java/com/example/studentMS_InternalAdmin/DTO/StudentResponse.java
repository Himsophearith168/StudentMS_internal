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
public class StudentResponse {

    private Long id;
    private String studentCode;
    private String fullName;
    private String gender;
    private Integer rollNumber;
    private Long classId;
    private String className;
    private LocalDateTime createdAt;
}
