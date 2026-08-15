package com.example.studentMS_InternalAdmin.Service;

import com.example.studentMS_InternalAdmin.DTO.StudentCreateRequest;
import com.example.studentMS_InternalAdmin.DTO.StudentResponse;
import com.example.studentMS_InternalAdmin.DTO.StudentUpdateRequest;

import java.util.List;

public interface StudentService {
    StudentResponse createStudent(StudentCreateRequest request);
    StudentResponse updateStudent(Long id, StudentUpdateRequest request);
    List<StudentResponse> getStudents();
    List<StudentResponse> getStudentsByClass(Long classId);
    StudentResponse getStudent(Long id);
    void deleteStudent(Long id);
}
