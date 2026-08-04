package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<ClassModel,Long> {
    Optional<ClassModel> findByClassName(String className);
    Optional<ClassModel> findBySemester(String semester);
    Optional<ClassModel> findByAcademicYear(String academicYear);

    boolean existsByClassName(String className);
    boolean existsBySemester(String semester);
    boolean existsByAcademicYear(String academicYear);

}
