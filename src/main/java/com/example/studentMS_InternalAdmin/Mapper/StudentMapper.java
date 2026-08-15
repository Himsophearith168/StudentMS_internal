package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.StudentCreateRequest;
import com.example.studentMS_InternalAdmin.DTO.StudentResponse;
import com.example.studentMS_InternalAdmin.Model.ClassModel;
import com.example.studentMS_InternalAdmin.Model.StudentModel;

public class StudentMapper {

    public static StudentModel toEntity(StudentCreateRequest dto, ClassModel classModel) {
        if (dto == null) return null;
        return StudentModel.builder()
                .studentCode(dto.getStudentCode())
                .fullName(dto.getFullName())
                .gender(dto.getGender())
                .rollNumber(dto.getRollNumber())
                .classModel(classModel)
                .build();
    }

    public static StudentResponse toDTO(StudentModel model) {
        if (model == null) return null;

        return StudentResponse.builder()
                .id(model.getId())
                .studentCode(model.getStudentCode())
                .fullName(model.getFullName())
                .gender(model.getGender())
                .rollNumber(model.getRollNumber())
                .classId(model.getClassModel() != null ? model.getClassModel().getId() : null)
                .className(model.getClassModel() != null ? model.getClassModel().getClassName() : null)
                .createdAt(model.getCreatedAt())
                .build();
    }
}
