package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.SemesterScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SemesterScoreRepository extends JpaRepository<SemesterScoreModel, Long> {
    List<SemesterScoreModel> findByStudentId(Long studentId);
    List<SemesterScoreModel> findByStudentIdAndSemester(Long studentId, Integer semester);
    Optional<SemesterScoreModel> findByStudentIdAndSubjectIdAndSemester(Long studentId, Long subjectId, Integer semester);
    List<SemesterScoreModel> findByStudentIdInAndSemester(List<Long> studentIds, Integer semester);
}
