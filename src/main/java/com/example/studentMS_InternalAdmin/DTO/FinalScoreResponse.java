package com.example.studentMS_InternalAdmin.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalScoreResponse {
    private Long id;
    private Long studentId;
    private String studentCode;
    private String studentName;
    private String gender;
    private Integer rollNumber;
    private String academicYear;
    private Double semester1Avg;
    private Double semester2Avg;
    private Double annualAvg;
    private Integer classRank;
    private String gradeMention;
}
