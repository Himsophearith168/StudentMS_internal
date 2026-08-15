package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.SubjectRequest;
import com.example.studentMS_InternalAdmin.DTO.SubjectResponse;
import com.example.studentMS_InternalAdmin.Model.SubjectModel;

public class SubjectMapper {

    public static SubjectModel toEntity(SubjectRequest dto) {
        if (dto == null) return null;
        return SubjectModel.builder()
                .subjectName(dto.getSubjectName())
                .maxScore(dto.getMaxScore() != null ? dto.getMaxScore() : 10.00)
                .build();
    }

    public static SubjectResponse toDTO(SubjectModel model) {
        if (model == null) return null;
        return SubjectResponse.builder()
                .id(model.getId())
                .subjectName(model.getSubjectName())
                .maxScore(model.getMaxScore())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
