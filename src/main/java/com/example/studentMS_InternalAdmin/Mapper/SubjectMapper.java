package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.SubjectRequest;
import com.example.studentMS_InternalAdmin.DTO.SubjectResponse;
import com.example.studentMS_InternalAdmin.Model.SubjectModel;

public class SubjectMapper {

    public static SubjectModel toEntity(SubjectRequest dto) {
        if (dto == null) return null;
        return SubjectModel.builder()
                .subjectName(dto.getSubjectName())
                .subjectDescription(dto.getSubjectDescription())
                .credit(dto.getCredit())
                .teacher(dto.getTeacher())
                .semester(dto.getSemester())
                .status(dto.getStatus() != null ? dto.getStatus() : "ACTIVE")
                .build();
    }

    public static SubjectResponse toDTO(SubjectModel model) {
        if (model == null) return null;
        return SubjectResponse.builder()
                .id(model.getId())
                .subjectName(model.getSubjectName())
                .subjectDescription(model.getSubjectDescription())
                .credit(model.getCredit())
                .teacher(model.getTeacher())
                .semester(model.getSemester())
                .status(model.getStatus())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
