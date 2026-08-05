package com.example.studentMS_InternalAdmin.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @Min(value = 0, message = "Quiz score cannot be negative")
    @Max(value = 100, message = "Quiz score cannot exceed 100")
    private Double quiz;

    @Min(value = 0, message = "Assignment score cannot be negative")
    @Max(value = 100, message = "Assignment score cannot exceed 100")
    private Double assignment;

    @Min(value = 0, message = "Midterm score cannot be negative")
    @Max(value = 100, message = "Midterm score cannot exceed 100")
    private Double midterm;

    @Min(value = 0, message = "Final exam score cannot be negative")
    @Max(value = 100, message = "Final exam score cannot exceed 100")
    private Double finalExam;

    @Min(value = 0, message = "Attendance score cannot be negative")
    @Max(value = 100, message = "Attendance score cannot exceed 100")
    private Double attendance;
}
