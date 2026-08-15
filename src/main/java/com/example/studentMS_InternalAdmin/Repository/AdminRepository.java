package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminModel, Long> {
    Optional<AdminModel> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<AdminModel> findByEmail(String email);
    boolean existsByEmail(String email);
}
