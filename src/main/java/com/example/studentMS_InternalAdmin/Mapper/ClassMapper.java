package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.ClassRequest;
import com.example.studentMS_InternalAdmin.DTO.ClassResponse;
import com.example.studentMS_InternalAdmin.Model.AdminModel;
import com.example.studentMS_InternalAdmin.Model.ClassModel;

public class ClassMapper {

    public static ClassModel toEntity(ClassRequest dto, AdminModel teacher) {
        if (dto == null) return null;
        return ClassModel.builder()
                .className(dto.getClassName())
                .academicYear(dto.getAcademicYear())
                .teacher(teacher)
                .build();
    }

    public static ClassResponse toDTO(ClassModel model) {
        if (model == null) return null;

        return ClassResponse.builder()
                .id(model.getId())
                .className(model.getClassName())
                .academicYear(model.getAcademicYear())
                .teacherId(model.getTeacher() != null ? model.getTeacher().getId() : null)
                .teacherName(model.getTeacher() != null ? model.getTeacher().getFullName() : null)
                .createdAt(model.getCreatedAt())
                .build();
    }
}
