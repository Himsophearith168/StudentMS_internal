package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.PermissionRequest;
import com.example.studentMS_InternalAdmin.DTO.PermissionResponse;
import com.example.studentMS_InternalAdmin.DTO.PermissionStatusUpdateRequest;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.PermissionMapper;
import com.example.studentMS_InternalAdmin.Model.PermissionModel;
import com.example.studentMS_InternalAdmin.Model.PermissionStatus;
import com.example.studentMS_InternalAdmin.Model.StudentModel;
import com.example.studentMS_InternalAdmin.Repository.PermissionRepository;
import com.example.studentMS_InternalAdmin.Repository.StudentRepository;
import com.example.studentMS_InternalAdmin.Service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository, StudentRepository studentRepository) {
        this.permissionRepository = permissionRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public PermissionResponse requestPermission(PermissionRequest request) {
        StudentModel student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        long pendingCount = permissionRepository.countByStudentIdAndStatus(student.getId(), PermissionStatus.PENDING);
        if (pendingCount >= 3) {
            throw new IllegalArgumentException("Student has too many pending permission requests (limit 3)");
        }

        int days = (int) ChronoUnit.DAYS.between(request.getStartDate(), request.getEndDate()) + 1;

        PermissionModel permission = PermissionModel.builder()
                .student(student)
                .reason(request.getReason())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .numberOfDays(days)
                .status(PermissionStatus.PENDING)
                .build();

        PermissionModel saved = permissionRepository.save(permission);
        return PermissionMapper.toDTO(saved);
    }

    @Override
    public PermissionResponse updatePermissionStatus(Long id, PermissionStatusUpdateRequest request) {
        PermissionModel existing = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission request not found with id: " + id));

        existing.setStatus(request.getStatus());
        existing.setApprovedBy(request.getApprovedBy() != null ? request.getApprovedBy() : "ADMIN");
        existing.setApprovedDate(LocalDateTime.now());
        existing.setRemark(request.getRemark());

        PermissionModel updated = permissionRepository.save(existing);
        return PermissionMapper.toDTO(updated);
    }

    @Override
    public PermissionResponse updatePermissionStatusByBody(PermissionStatusUpdateRequest request) {
        if (request.getPermissionId() == null) {
            throw new IllegalArgumentException("Permission ID must be provided in the request body");
        }
        return updatePermissionStatus(request.getPermissionId(), request);
    }

    @Override
    public PermissionResponse updateLatestPermissionByStudent(Long studentId, PermissionStatusUpdateRequest request) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }

        List<PermissionModel> pendingRequests = permissionRepository
                .findByStudentIdAndStatusOrderByIdDesc(studentId, PermissionStatus.PENDING);

        if (pendingRequests.isEmpty()) {
            throw new ResourceNotFoundException("No PENDING permission request found for student id: " + studentId);
        }

        PermissionModel latestPending = pendingRequests.get(0);
        latestPending.setStatus(request.getStatus());
        latestPending.setApprovedBy(request.getApprovedBy() != null ? request.getApprovedBy() : "TEACHER");
        latestPending.setApprovedDate(LocalDateTime.now());
        latestPending.setRemark(request.getRemark());

        PermissionModel updated = permissionRepository.save(latestPending);
        return PermissionMapper.toDTO(updated);
    }


    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(PermissionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionResponse> getPermissionsByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        return permissionRepository.findByStudentId(studentId)
                .stream()
                .map(PermissionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PermissionResponse getPermission(Long id) {
        PermissionModel permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission request not found with id: " + id));
        return PermissionMapper.toDTO(permission);
    }
}
