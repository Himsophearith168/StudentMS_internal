package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.ClassRequest;
import com.example.studentMS_InternalAdmin.DTO.ClassResponse;
import com.example.studentMS_InternalAdmin.Model.ClassModel;

public class ClassMapper {

    public static ClassModel toEntity(ClassRequest dto){
        if(dto == null) return null;
        return ClassModel.builder()
                .className(dto.getClassName())
                .academicYear(dto.getAcademicYear())
                .semester(dto.getSemester())
                .description(dto.getDescription())
                .maxStudents(dto.getMaxStudents())
                .build();
    }

    // Deprecated alias retained for compatibility
    public static ClassModel toEntitt(ClassRequest dto){
        return toEntity(dto);
    }

    public static ClassResponse toDTO(ClassModel model){
        if(model == null) return null;

        return ClassResponse.builder()
                .id(model.getId())
                .className(model.getClassName())
                .academicYear(model.getAcademicYear())
                .semester(model.getSemester())
                .description(model.getDescription())
                .maxStudents(model.getMaxStudents())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
