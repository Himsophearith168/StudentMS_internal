package com.example.studentMS_InternalAdmin.DTO;

import com.example.studentMS_InternalAdmin.Model.PermissionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionStatusUpdateRequest {

    @NotNull(message = "Status is required (APPROVED or REJECTED)")
    private PermissionStatus status;

    private String approvedBy;

    private String remark;
}
