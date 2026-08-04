package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.ClassRequest;
import com.example.studentMS_InternalAdmin.DTO.ClassResponse;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.ClassMapper;
import com.example.studentMS_InternalAdmin.Model.ClassModel;
import com.example.studentMS_InternalAdmin.Repository.ClassRepository;
import com.example.studentMS_InternalAdmin.Service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceimpl implements ClassService {

    private final ClassRepository classRepository;

    @Autowired
    public ClassServiceimpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public ClassResponse createClass(ClassRequest request) {
        if (classRepository.existsByClassName(request.getClassName())) {
            throw new IllegalArgumentException("Class name already exists: " + request.getClassName());
        }
        ClassModel classModel = ClassMapper.toEntity(request);
        ClassModel savedClass = classRepository.save(classModel);
        return ClassMapper.toDTO(savedClass);
    }

    @Override
    public ClassResponse updateClass(Long id, ClassRequest request) {
        ClassModel existingClass = classRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + id));

        if (!existingClass.getClassName().equals(request.getClassName()) &&
                classRepository.existsByClassName(request.getClassName())) {
            throw new IllegalArgumentException("Class name already exists: " + request.getClassName());
        }

        existingClass.setClassName(request.getClassName());
        existingClass.setAcademicYear(request.getAcademicYear());
        existingClass.setSemester(request.getSemester());
        existingClass.setDescription(request.getDescription());
        existingClass.setMaxStudents(request.getMaxStudents());

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
