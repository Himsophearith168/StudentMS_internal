package com.example.studentMS_InternalAdmin.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassResponse {
    private Long id;
    private String className;
    private String academicYear;
    private String semester;
    private String description;
    private Integer maxStudents;
    private LocalDateTime createdAt;

}
