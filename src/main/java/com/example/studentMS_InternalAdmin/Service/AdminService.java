package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.AdminRequest;
import com.example.studentMS_InternalAdmin.DTO.AdminResponse;

import java.util.List;

public interface AdminService {
    AdminResponse createAdmin(AdminRequest request);
    AdminResponse updateAdmin(Long id, AdminRequest request);
    List<AdminResponse> getAllAdmins();
    AdminResponse getAdminById(Long id);
    void deleteAdmin(Long id);
}
