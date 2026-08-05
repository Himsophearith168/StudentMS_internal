package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.SubjectRequest;
import com.example.studentMS_InternalAdmin.DTO.SubjectResponse;

import java.util.List;

public interface SubjectService {
    SubjectResponse createSubject(SubjectRequest request);
    SubjectResponse updateSubject(Long id, SubjectRequest request);
    List<SubjectResponse> getSubjects();
    SubjectResponse getSubject(Long id);
    void deleteSubject(Long id);
}
