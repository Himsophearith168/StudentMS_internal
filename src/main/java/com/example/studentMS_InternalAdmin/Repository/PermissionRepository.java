package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.PermissionModel;
import com.example.studentMS_InternalAdmin.Model.PermissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<PermissionModel, Long> {
    List<PermissionModel> findByStudentId(Long studentId);
    List<PermissionModel> findByStatus(PermissionStatus status);
    long countByStudentIdAndStatus(Long studentId, PermissionStatus status);
}
