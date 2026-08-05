package com.example.studentMS_InternalAdmin.DTO;

import com.example.studentMS_InternalAdmin.Model.PermissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {

    private Long id;
    private Long studentId;
    private String studentName;
    private String studentCode;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfDays;
    private PermissionStatus status;
    private String approvedBy;
    private LocalDateTime approvedDate;
    private String remark;
    private LocalDateTime createdAt;
}
