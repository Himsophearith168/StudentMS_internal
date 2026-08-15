package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.*;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.MonthlyScoreMapper;
import com.example.studentMS_InternalAdmin.Mapper.SemesterScoreMapper;
import com.example.studentMS_InternalAdmin.Model.MonthlyScoreModel;
import com.example.studentMS_InternalAdmin.Model.SemesterScoreModel;
import com.example.studentMS_InternalAdmin.Model.StudentModel;
import com.example.studentMS_InternalAdmin.Model.SubjectModel;
import com.example.studentMS_InternalAdmin.Repository.MonthlyScoreRepository;
import com.example.studentMS_InternalAdmin.Repository.SemesterScoreRepository;
import com.example.studentMS_InternalAdmin.Repository.StudentRepository;
import com.example.studentMS_InternalAdmin.Repository.SubjectRepository;
import com.example.studentMS_InternalAdmin.Service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final MonthlyScoreRepository monthlyScoreRepository;
    private final SemesterScoreRepository semesterScoreRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public ScoreServiceImpl(MonthlyScoreRepository monthlyScoreRepository,
                            SemesterScoreRepository semesterScoreRepository,
                            StudentRepository studentRepository,
                            SubjectRepository subjectRepository) {
        this.monthlyScoreRepository = monthlyScoreRepository;
        this.semesterScoreRepository = semesterScoreRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public MonthlyScoreResponse recordMonthlyScore(MonthlyScoreRequest request) {
        StudentModel student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));

        SubjectModel subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + request.getSubjectId()));

        MonthlyScoreModel entity = MonthlyScoreMapper.toEntity(request, student, subject);
        MonthlyScoreModel saved = monthlyScoreRepository.save(entity);
        return MonthlyScoreMapper.toDTO(saved);
    }

    @Override
    public List<MonthlyScoreResponse> getMonthlyScoresByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        return monthlyScoreRepository.findByStudentId(studentId)
                .stream()
                .map(MonthlyScoreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MonthlyScoreResponse> getMonthlyScoresByStudentAndSemester(Long studentId, Integer semester) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        return monthlyScoreRepository.findByStudentIdAndSemester(studentId, semester)
                .stream()
                .map(MonthlyScoreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SemesterScoreResponse recordSemesterScore(SemesterScoreRequest request) {
        StudentModel student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));

        SubjectModel subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + request.getSubjectId()));

        Optional<SemesterScoreModel> existing = semesterScoreRepository.findByStudentIdAndSubjectIdAndSemester(
                request.getStudentId(), request.getSubjectId(), request.getSemester());

        SemesterScoreModel entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setExamScore(request.getExamScore());
        } else {
            entity = SemesterScoreMapper.toEntity(request, student, subject);
        }

        SemesterScoreModel saved = semesterScoreRepository.save(entity);
        return SemesterScoreMapper.toDTO(saved);
    }

    @Override
    public List<SemesterScoreResponse> getSemesterScoresByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        return semesterScoreRepository.findByStudentId(studentId)
                .stream()
                .map(SemesterScoreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SemesterScoreResponse> getSemesterScoresByStudentAndSemester(Long studentId, Integer semester) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        return semesterScoreRepository.findByStudentIdAndSemester(studentId, semester)
                .stream()
                .map(SemesterScoreMapper::toDTO)
                .collect(Collectors.toList());
    }
}
