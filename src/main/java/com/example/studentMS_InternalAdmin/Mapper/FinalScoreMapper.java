package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.FinalScoreResponse;
import com.example.studentMS_InternalAdmin.Model.FinalScoreModel;

public class FinalScoreMapper {

    public static FinalScoreResponse toDTO(FinalScoreModel model) {
        if (model == null) return null;
        return FinalScoreResponse.builder()
                .id(model.getId())
                .studentId(model.getStudent() != null ? model.getStudent().getId() : null)
                .studentCode(model.getStudent() != null ? model.getStudent().getStudentCode() : null)
                .studentName(model.getStudent() != null ? model.getStudent().getFullName() : null)
                .gender(model.getStudent() != null ? model.getStudent().getGender() : null)
                .rollNumber(model.getStudent() != null ? model.getStudent().getRollNumber() : null)
                .academicYear(model.getAcademicYear())
                .semester1Avg(model.getSemester1Avg())
                .semester2Avg(model.getSemester2Avg())
                .annualAvg(model.getAnnualAvg())
                .classRank(model.getClassRank())
                .gradeMention(model.getGradeMention())
                .build();
    }
}
