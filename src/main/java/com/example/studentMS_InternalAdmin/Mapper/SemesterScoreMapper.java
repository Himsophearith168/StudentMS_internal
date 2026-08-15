package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.SemesterScoreRequest;
import com.example.studentMS_InternalAdmin.DTO.SemesterScoreResponse;
import com.example.studentMS_InternalAdmin.Model.SemesterScoreModel;
import com.example.studentMS_InternalAdmin.Model.StudentModel;
import com.example.studentMS_InternalAdmin.Model.SubjectModel;

public class SemesterScoreMapper {

    public static SemesterScoreModel toEntity(SemesterScoreRequest dto, StudentModel student, SubjectModel subject) {
        if (dto == null) return null;
        return SemesterScoreModel.builder()
                .student(student)
                .subject(subject)
                .semester(dto.getSemester())
                .examScore(dto.getExamScore())
                .build();
    }

    public static SemesterScoreResponse toDTO(SemesterScoreModel model) {
        if (model == null) return null;
        return SemesterScoreResponse.builder()
                .id(model.getId())
                .studentId(model.getStudent() != null ? model.getStudent().getId() : null)
                .studentName(model.getStudent() != null ? model.getStudent().getFullName() : null)
                .subjectId(model.getSubject() != null ? model.getSubject().getId() : null)
                .subjectName(model.getSubject() != null ? model.getSubject().getSubjectName() : null)
                .semester(model.getSemester())
                .examScore(model.getExamScore())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
