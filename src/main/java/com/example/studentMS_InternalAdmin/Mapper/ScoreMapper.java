package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.ScoreResponse;
import com.example.studentMS_InternalAdmin.Model.ScoreModel;

public class ScoreMapper {

    public static ScoreResponse toDTO(ScoreModel model) {
        if (model == null) return null;

        return ScoreResponse.builder()
                .id(model.getId())
                .studentId(model.getStudent() != null ? model.getStudent().getId() : null)
                .studentName(model.getStudent() != null ? model.getStudent().getFullName() : null)
                .studentCode(model.getStudent() != null ? model.getStudent().getStudentCode() : null)
                .subjectId(model.getSubject() != null ? model.getSubject().getId() : null)
                .subjectName(model.getSubject() != null ? model.getSubject().getSubjectName() : null)
                .quiz(model.getQuiz())
                .assignment(model.getAssignment())
                .midterm(model.getMidterm())
                .finalExam(model.getFinalExam())
                .attendance(model.getAttendance())
                .totalScore(model.getTotalScore())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
