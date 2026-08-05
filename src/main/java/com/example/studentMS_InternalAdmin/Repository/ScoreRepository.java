package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.ScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<ScoreModel, Long> {
    List<ScoreModel> findByStudentId(Long studentId);
    List<ScoreModel> findBySubjectId(Long subjectId);
    Optional<ScoreModel> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
