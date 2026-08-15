package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.AdminRequest;
import com.example.studentMS_InternalAdmin.DTO.AdminResponse;
import com.example.studentMS_InternalAdmin.Model.AdminModel;

public class AdminMapper {

    public static AdminModel toEntity(AdminRequest dto, String encodedPassword) {
        if (dto == null) return null;
        return AdminModel.builder()
                .username(dto.getUsername())
                .passwordHash(encodedPassword)
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .role(dto.getRole() != null ? dto.getRole() : "TEACHER")
                .build();
    }

    public static AdminResponse toDTO(AdminModel model) {
        if (model == null) return null;
        return AdminResponse.builder()
                .id(model.getId())
                .username(model.getUsername())
                .fullName(model.getFullName())
                .email(model.getEmail())
                .role(model.getRole())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
