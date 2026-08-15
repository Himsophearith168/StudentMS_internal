package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.ClassAnalyticsResponse;
import com.example.studentMS_InternalAdmin.DTO.FinalScoreResponse;

import java.util.List;

public interface GradeCalculationService {
    FinalScoreResponse calculateStudentAnnualScore(Long studentId, String academicYear);
    List<FinalScoreResponse> calculateAndRankClass(Long classId);
    List<FinalScoreResponse> getClassFinalScores(Long classId);
    ClassAnalyticsResponse getClassAnalytics(Long classId);
    String evaluateGradeMention(Double score);
}
