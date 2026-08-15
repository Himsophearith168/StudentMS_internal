package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.StudentCreateRequest;
import com.example.studentMS_InternalAdmin.DTO.StudentResponse;
import com.example.studentMS_InternalAdmin.DTO.StudentUpdateRequest;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.StudentMapper;
import com.example.studentMS_InternalAdmin.Model.ClassModel;
import com.example.studentMS_InternalAdmin.Model.StudentModel;
import com.example.studentMS_InternalAdmin.Repository.ClassRepository;
import com.example.studentMS_InternalAdmin.Repository.StudentRepository;
import com.example.studentMS_InternalAdmin.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    @Override
    public StudentResponse createStudent(StudentCreateRequest request) {
        if (studentRepository.existsByStudentCode(request.getStudentCode())) {
            throw new IllegalArgumentException("Student code already exists: " + request.getStudentCode());
        }

        ClassModel classModel = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + request.getClassId()));

        StudentModel student = StudentMapper.toEntity(request, classModel);
        StudentModel savedStudent = studentRepository.save(student);
        return StudentMapper.toDTO(savedStudent);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest request) {
        StudentModel existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        if (request.getStudentCode() != null && !request.getStudentCode().equals(existing.getStudentCode())) {
            if (studentRepository.existsByStudentCode(request.getStudentCode())) {
                throw new IllegalArgumentException("Student code already exists: " + request.getStudentCode());
            }
            existing.setStudentCode(request.getStudentCode());
        }

        if (request.getFullName() != null) {
            existing.setFullName(request.getFullName());
        }
        if (request.getGender() != null) {
            existing.setGender(request.getGender());
        }
        if (request.getRollNumber() != null) {
            existing.setRollNumber(request.getRollNumber());
        }
        if (request.getClassId() != null) {
            ClassModel classModel = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + request.getClassId()));
            existing.setClassModel(classModel);
        }

        StudentModel updated = studentRepository.save(existing);
        return StudentMapper.toDTO(updated);
    }

    @Override
    public List<StudentResponse> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> getStudentsByClass(Long classId) {
        if (!classRepository.existsById(classId)) {
            throw new ResourceNotFoundException("Class not found with id: " + classId);
        }
        return studentRepository.findByClassModelIdOrderByRollNumberAsc(classId)
                .stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse getStudent(Long id) {
        StudentModel student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return StudentMapper.toDTO(student);
    }

    @Override
    public void deleteStudent(Long id) {
        StudentModel student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }
}
