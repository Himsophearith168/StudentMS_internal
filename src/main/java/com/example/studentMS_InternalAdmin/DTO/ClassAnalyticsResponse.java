package com.example.studentMS_InternalAdmin.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassAnalyticsResponse {

    private Long classId;
    private String className;
    private String academicYear;
    private String teacherName;
    private Integer totalEvaluatedRoster;
    private Integer femaleCount;
    private Double femalePercentage;
    private Integer maleCount;
    private Double malePercentage;
    private Double classMonthlyMeanScore;
    private Double scoreStandardDeviation;
    private Double minimumScore;
    private Double maximumScore;
    private Double overallPassRate;
    private Integer passedCount;
    private Integer failedCount;
}
