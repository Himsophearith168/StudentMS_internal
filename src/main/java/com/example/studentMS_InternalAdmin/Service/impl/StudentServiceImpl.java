package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.StudentCreateRequest;
import com.example.studentMS_InternalAdmin.DTO.StudentResponse;
import com.example.studentMS_InternalAdmin.DTO.StudentUpdateRequest;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.StudentMapper;
import com.example.studentMS_InternalAdmin.Model.ClassModel;
import com.example.studentMS_InternalAdmin.Model.Role;
import com.example.studentMS_InternalAdmin.Model.StudentModel;
import com.example.studentMS_InternalAdmin.Model.SubjectModel;
import com.example.studentMS_InternalAdmin.Model.UserModel;
import com.example.studentMS_InternalAdmin.Repository.ClassRepository;
import com.example.studentMS_InternalAdmin.Repository.StudentRepository;
import com.example.studentMS_InternalAdmin.Repository.SubjectRepository;
import com.example.studentMS_InternalAdmin.Repository.UserRepository;
import com.example.studentMS_InternalAdmin.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              UserRepository userRepository,
                              ClassRepository classRepository,
                              SubjectRepository subjectRepository,
                              PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.classRepository = classRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StudentResponse createStudent(StudentCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + request.getUsername());
        }
        if (studentRepository.existsByStudentCode(request.getStudentCode())) {
            throw new IllegalArgumentException("Student code already exists: " + request.getStudentCode());
        }

        UserModel user = UserModel.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .build();

        UserModel savedUser = userRepository.save(user);

        ClassModel classModel = null;
        if (request.getClassId() != null) {
            classModel = classRepository.findById(request.getClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + request.getClassId()));
        }

        StudentModel student = StudentModel.builder()
                .studentCode(request.getStudentCode())
                .user(savedUser)
                .fullName(request.getFullName())
                .gender(request.getGender())
                .dob(request.getDob())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .status("ACTIVE")
                .classModel(classModel)
                .build();

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
        UserModel user = existing.getUser();
        if (user != null) {
            boolean userUpdated = false;
            if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
                if (userRepository.existsByUsername(request.getUsername())) {
                    throw new IllegalArgumentException("Username already exists: " + request.getUsername());
                }
                user.setUsername(request.getUsername());
                userUpdated = true;
            }
            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userUpdated = true;
            }
            if (userUpdated) {
                userRepository.save(user);
            }
        }

        existing.setFullName(request.getFullName());
        existing.setGender(request.getGender());
        existing.setDob(request.getDob());
        existing.setPhone(request.getPhone());
        existing.setEmail(request.getEmail());
        existing.setAddress(request.getAddress());
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
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
