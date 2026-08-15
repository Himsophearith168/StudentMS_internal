package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.MonthlyScoreRequest;
import com.example.studentMS_InternalAdmin.DTO.MonthlyScoreResponse;
import com.example.studentMS_InternalAdmin.Model.MonthlyScoreModel;
import com.example.studentMS_InternalAdmin.Model.StudentModel;
import com.example.studentMS_InternalAdmin.Model.SubjectModel;

public class MonthlyScoreMapper {

    public static MonthlyScoreModel toEntity(MonthlyScoreRequest dto, StudentModel student, SubjectModel subject) {
        if (dto == null) return null;
        return MonthlyScoreModel.builder()
                .student(student)
                .subject(subject)
                .semester(dto.getSemester())
                .monthName(dto.getMonthName())
                .score(dto.getScore())
                .build();
    }

    public static MonthlyScoreResponse toDTO(MonthlyScoreModel model) {
        if (model == null) return null;
        return MonthlyScoreResponse.builder()
                .id(model.getId())
                .studentId(model.getStudent() != null ? model.getStudent().getId() : null)
                .studentName(model.getStudent() != null ? model.getStudent().getFullName() : null)
                .subjectId(model.getSubject() != null ? model.getSubject().getId() : null)
                .subjectName(model.getSubject() != null ? model.getSubject().getSubjectName() : null)
                .semester(model.getSemester())
                .monthName(model.getMonthName())
                .score(model.getScore())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
