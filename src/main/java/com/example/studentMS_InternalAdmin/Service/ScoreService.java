package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.*;

import java.util.List;

public interface ScoreService {
    MonthlyScoreResponse recordMonthlyScore(MonthlyScoreRequest request);
    List<MonthlyScoreResponse> getMonthlyScoresByStudent(Long studentId);
    List<MonthlyScoreResponse> getMonthlyScoresByStudentAndSemester(Long studentId, Integer semester);

    SemesterScoreResponse recordSemesterScore(SemesterScoreRequest request);
    List<SemesterScoreResponse> getSemesterScoresByStudent(Long studentId);
    List<SemesterScoreResponse> getSemesterScoresByStudentAndSemester(Long studentId, Integer semester);
}
