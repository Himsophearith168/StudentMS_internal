package com.example.studentMS_InternalAdmin.Repository;

import com.example.studentMS_InternalAdmin.Model.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectModel, Long> {
    Optional<SubjectModel> findBySubjectName(String subjectName);
    boolean existsBySubjectName(String subjectName);
}
