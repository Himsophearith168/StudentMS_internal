package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.StudentResponse;
import com.example.studentMS_InternalAdmin.Model.StudentModel;

import java.util.stream.Collectors;

public class StudentMapper {

    public static StudentResponse toDTO(StudentModel model) {
        if (model == null) return null;

        return StudentResponse.builder()
                .id(model.getId())
                .studentCode(model.getStudentCode())
                .username(model.getUser() != null ? model.getUser().getUsername() : null)
                .fullName(model.getFullName())
                .gender(model.getGender())
                .dob(model.getDob())
                .phone(model.getPhone())
                .email(model.getEmail())
                .address(model.getAddress())
                .status(model.getStatus())
                .className(model.getClassModel() != null ? ClassMapper.toDTO(model.getClassModel()) : null)
                .createdAt(model.getCreatedAt())
                .build();
    }
}
