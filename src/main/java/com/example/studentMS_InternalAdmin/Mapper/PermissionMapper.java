package com.example.studentMS_InternalAdmin.Mapper;

import com.example.studentMS_InternalAdmin.DTO.PermissionResponse;
import com.example.studentMS_InternalAdmin.Model.PermissionModel;

public class PermissionMapper {

    public static PermissionResponse toDTO(PermissionModel model) {
        if (model == null) return null;

        return PermissionResponse.builder()
                .id(model.getId())
                .studentId(model.getStudent() != null ? model.getStudent().getId() : null)
                .studentName(model.getStudent() != null ? model.getStudent().getFullName() : null)
                .studentCode(model.getStudent() != null ? model.getStudent().getStudentCode() : null)
                .reason(model.getReason())
                .startDate(model.getStartDate())
                .endDate(model.getEndDate())
                .numberOfDays(model.getNumberOfDays())
                .status(model.getStatus())
                .approvedBy(model.getApprovedBy())
                .approvedDate(model.getApprovedDate())
                .remark(model.getRemark())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
