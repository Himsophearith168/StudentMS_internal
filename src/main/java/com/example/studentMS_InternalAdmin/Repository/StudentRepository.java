package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    Optional<StudentModel> findByStudentCode(String studentCode);
    Optional<StudentModel> findByUserId(Long userId);
    boolean existsByStudentCode(String studentCode);
    boolean existsByEmail(String email);
}
