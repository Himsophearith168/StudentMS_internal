package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.ClassRequest;
import com.example.studentMS_InternalAdmin.DTO.ClassResponse;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.ClassMapper;
import com.example.studentMS_InternalAdmin.Model.AdminModel;
import com.example.studentMS_InternalAdmin.Model.ClassModel;
import com.example.studentMS_InternalAdmin.Repository.AdminRepository;
import com.example.studentMS_InternalAdmin.Repository.ClassRepository;
import com.example.studentMS_InternalAdmin.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceimpl implements ClassService {

    private final ClassRepository classRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public ClassServiceimpl(ClassRepository classRepository, AdminRepository adminRepository) {
        this.classRepository = classRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public ClassResponse createClass(ClassRequest request) {
        AdminModel teacher = adminRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + request.getTeacherId()));

        if (classRepository.existsByTeacherIdAndAcademicYear(request.getTeacherId(), request.getAcademicYear())) {
            throw new IllegalArgumentException("Teacher ID " + request.getTeacherId() + 
                    " is already assigned as head teacher to another class in academic year " + request.getAcademicYear());
        }

        ClassModel classModel = ClassMapper.toEntity(request, teacher);
        ClassModel savedClass = classRepository.save(classModel);
        return ClassMapper.toDTO(savedClass);
    }

    @Override
    public ClassResponse updateClass(Long id, ClassRequest request) {
        ClassModel existingClass = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));

        AdminModel teacher = adminRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + request.getTeacherId()));

        boolean teacherOrYearChanged = !existingClass.getTeacher().getId().equals(request.getTeacherId()) ||
                !existingClass.getAcademicYear().equals(request.getAcademicYear());

        if (teacherOrYearChanged && classRepository.existsByTeacherIdAndAcademicYear(request.getTeacherId(), request.getAcademicYear())) {
            throw new IllegalArgumentException("Teacher ID " + request.getTeacherId() + 
                    " is already assigned as head teacher to another class in academic year " + request.getAcademicYear());
        }

        existingClass.setClassName(request.getClassName());
        existingClass.setAcademicYear(request.getAcademicYear());
        existingClass.setTeacher(teacher);

        ClassModel updatedClass = classRepository.save(existingClass);
        return ClassMapper.toDTO(updatedClass);
    }

    @Override
    public List<ClassResponse> getClasses() {
        return classRepository.findAll()
                .stream()
                .map(ClassMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClassResponse getClass(Long id) {
        ClassModel classModel = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
        return ClassMapper.toDTO(classModel);
    }

    @Override
    public void deleteClass(Long id) {
        ClassModel classModel = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));
        classRepository.delete(classModel);
    }
}
