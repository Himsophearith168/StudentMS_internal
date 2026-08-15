package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.FinalScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinalScoreRepository extends JpaRepository<FinalScoreModel, Long> {
    Optional<FinalScoreModel> findByStudentIdAndAcademicYear(Long studentId, String academicYear);
    List<FinalScoreModel> findByStudentIdInAndAcademicYear(List<Long> studentIds, String academicYear);
    List<FinalScoreModel> findByStudentId(Long studentId);
}
