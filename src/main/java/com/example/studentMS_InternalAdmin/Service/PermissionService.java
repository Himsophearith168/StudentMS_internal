package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.PermissionRequest;
import com.example.studentMS_InternalAdmin.DTO.PermissionResponse;
import com.example.studentMS_InternalAdmin.DTO.PermissionStatusUpdateRequest;

import java.util.List;

public interface PermissionService {
    PermissionResponse requestPermission(PermissionRequest request);
    PermissionResponse updatePermissionStatus(Long id, PermissionStatusUpdateRequest request);
    PermissionResponse updatePermissionStatusByBody(PermissionStatusUpdateRequest request);
    PermissionResponse updateLatestPermissionByStudent(Long studentId, PermissionStatusUpdateRequest request);
    List<PermissionResponse> getAllPermissions();
    List<PermissionResponse> getPermissionsByStudent(Long studentId);
    PermissionResponse getPermission(Long id);
}

