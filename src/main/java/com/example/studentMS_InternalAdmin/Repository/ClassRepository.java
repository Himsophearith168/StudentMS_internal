package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassModel, Long> {
    Optional<ClassModel> findByClassName(String className);
    List<ClassModel> findByAcademicYear(String academicYear);
    List<ClassModel> findByTeacherId(Long teacherId);
    Optional<ClassModel> findByTeacherIdAndAcademicYear(Long teacherId, String academicYear);

    boolean existsByClassName(String className);
    boolean existsByAcademicYear(String academicYear);
    boolean existsByTeacherIdAndAcademicYear(Long teacherId, String academicYear);
}
