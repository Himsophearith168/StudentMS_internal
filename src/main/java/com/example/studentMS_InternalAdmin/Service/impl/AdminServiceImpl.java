package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.AdminRequest;
import com.example.studentMS_InternalAdmin.DTO.AdminResponse;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.AdminMapper;
import com.example.studentMS_InternalAdmin.Model.AdminModel;
import com.example.studentMS_InternalAdmin.Repository.AdminRepository;
import com.example.studentMS_InternalAdmin.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdminResponse createAdmin(AdminRequest request) {
        if (adminRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }
        if (request.getEmail() != null && adminRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        AdminModel adminModel = AdminMapper.toEntity(request, encodedPassword);
        AdminModel saved = adminRepository.save(adminModel);
        return AdminMapper.toDTO(saved);
    }

    @Override
    public AdminResponse updateAdmin(Long id, AdminRequest request) {
        AdminModel existing = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin staff not found with id: " + id));

        if (!existing.getUsername().equals(request.getUsername()) && adminRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }

        existing.setUsername(request.getUsername());
        existing.setFullName(request.getFullName());
        if (request.getEmail() != null) {
            existing.setEmail(request.getEmail());
        }
        if (request.getRole() != null) {
            existing.setRole(request.getRole());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        AdminModel updated = adminRepository.save(existing);
        return AdminMapper.toDTO(updated);
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(AdminMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdminResponse getAdminById(Long id) {
        AdminModel admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin staff not found with id: " + id));
        return AdminMapper.toDTO(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        AdminModel admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin staff not found with id: " + id));
        adminRepository.delete(admin);
    }
}
