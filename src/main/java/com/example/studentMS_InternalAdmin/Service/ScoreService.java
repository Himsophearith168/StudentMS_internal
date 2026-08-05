package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.ScoreRequest;
import com.example.studentMS_InternalAdmin.DTO.ScoreResponse;

import java.util.List;

public interface ScoreService {
    ScoreResponse recordScore(ScoreRequest request);
    ScoreResponse updateScore(Long id, ScoreRequest request);
    List<ScoreResponse> getAllScores();
    List<ScoreResponse> getScoresByStudent(Long studentId);
    List<ScoreResponse> getScoresBySubject(Long subjectId);
    ScoreResponse getScore(Long id);
    void deleteScore(Long id);
}
