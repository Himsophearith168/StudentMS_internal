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
public class ScoreResponse {

    private Long id;
    private Long studentId;
    private String studentName;
    private String studentCode;
    private Long subjectId;
    private String subjectName;
    private Double quiz;
    private Double assignment;
    private Double midterm;
    private Double finalExam;
    private Double attendance;
    private Double totalScore;
    private LocalDateTime createdAt;
}
