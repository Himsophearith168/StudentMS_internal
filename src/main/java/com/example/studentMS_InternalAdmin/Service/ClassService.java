package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.ClassRequest;
import com.example.studentMS_InternalAdmin.DTO.ClassResponse;

import java.util.List;

public interface ClassService {
    ClassResponse createClass(ClassRequest request);
    ClassResponse updateClass(Long id,ClassRequest request);
    List<ClassResponse> getClasses();
    ClassResponse getClass(Long id);
    void deleteClass(Long id);

}
