package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.MonthlyScoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyScoreRepository extends JpaRepository<MonthlyScoreModel, Long> {
    List<MonthlyScoreModel> findByStudentId(Long studentId);
    List<MonthlyScoreModel> findByStudentIdAndSemester(Long studentId, Integer semester);
    List<MonthlyScoreModel> findByStudentIdAndSubjectIdAndSemester(Long studentId, Long subjectId, Integer semester);
    List<MonthlyScoreModel> findByStudentIdInAndSemester(List<Long> studentIds, Integer semester);
}
