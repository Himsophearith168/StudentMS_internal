package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.ClassRequest;
import com.example.studentMS_InternalAdmin.DTO.ClassResponse;
import com.example.studentMS_InternalAdmin.Model.ClassModel;

public class ClassMapper {
    public static ClassModel toEntitt(ClassRequest dto){
        if(dto == null) return null;
        return  ClassModel.builder()
                .className(dto.getClassName())
                .academicYear(dto.getAcademicYear())
                .semester(dto.getSemester())
                .description(dto.getDescription())
                .maxStudents(dto.getMaxStudents())
                .build();
    }

    public static ClassResponse toDTO(ClassModel dto){
        if(dto == null) return null;

        return ClassResponse.builder()
                .id(dto.getId())
                .className(dto.getClassName())
                .semester(dto.getSemester())
                .academicYear(dto.getAcademicYear())
                .description(dto.getDescription())
                .maxStudents(dto.getMaxStudents())
                .build();
    }

}
