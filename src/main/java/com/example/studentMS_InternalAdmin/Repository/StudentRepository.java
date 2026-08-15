package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    Optional<StudentModel> findByStudentCode(String studentCode);
    boolean existsByStudentCode(String studentCode);
    List<StudentModel> findByClassModelId(Long classId);
    List<StudentModel> findByClassModelIdOrderByRollNumberAsc(Long classId);
}
