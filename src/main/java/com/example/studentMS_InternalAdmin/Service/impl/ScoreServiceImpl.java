package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.ScoreRequest;
import com.example.studentMS_InternalAdmin.DTO.ScoreResponse;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.ScoreMapper;
import com.example.studentMS_InternalAdmin.Model.ScoreModel;
import com.example.studentMS_InternalAdmin.Model.StudentModel;
import com.example.studentMS_InternalAdmin.Model.SubjectModel;
import com.example.studentMS_InternalAdmin.Repository.ScoreRepository;
import com.example.studentMS_InternalAdmin.Repository.StudentRepository;
import com.example.studentMS_InternalAdmin.Repository.SubjectRepository;
import com.example.studentMS_InternalAdmin.Service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public ScoreResponse recordScore(ScoreRequest request) {
        StudentModel student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));

        SubjectModel subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + request.getSubjectId()));

        if (scoreRepository.existsByStudentIdAndSubjectId(student.getId(), subject.getId())) {
            throw new IllegalArgumentException("Score record already exists for this student and subject");
        }

        ScoreModel scoreModel = ScoreModel.builder()
                .student(student)
                .subject(subject)
                .quiz(request.getQuiz() != null ? request.getQuiz() : 0.0)
                .assignment(request.getAssignment() != null ? request.getAssignment() : 0.0)
                .midterm(request.getMidterm() != null ? request.getMidterm() : 0.0)
                .finalExam(request.getFinalExam() != null ? request.getFinalExam() : 0.0)
                .attendance(request.getAttendance() != null ? request.getAttendance() : 0.0)
                .build();

        ScoreModel saved = scoreRepository.save(scoreModel);
        return ScoreMapper.toDTO(saved);
    }

    @Override
    public ScoreResponse updateScore(Long id, ScoreRequest request) {
        ScoreModel existing = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score record not found with id: " + id));

        if (request.getQuiz() != null) existing.setQuiz(request.getQuiz());
        if (request.getAssignment() != null) existing.setAssignment(request.getAssignment());
        if (request.getMidterm() != null) existing.setMidterm(request.getMidterm());
        if (request.getFinalExam() != null) existing.setFinalExam(request.getFinalExam());
        if (request.getAttendance() != null) existing.setAttendance(request.getAttendance());

        ScoreModel updated = scoreRepository.save(existing);
        return ScoreMapper.toDTO(updated);
    }

    @Override
    public List<ScoreResponse> getAllScores() {
        return scoreRepository.findAll()
                .stream()
                .map(ScoreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScoreResponse> getScoresByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with id: " + studentId);
        }
        return scoreRepository.findByStudentId(studentId)
                .stream()
                .map(ScoreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScoreResponse> getScoresBySubject(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id: " + subjectId);
        }
        return scoreRepository.findBySubjectId(subjectId)
                .stream()
                .map(ScoreMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ScoreResponse getScore(Long id) {
        ScoreModel scoreModel = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score record not found with id: " + id));
        return ScoreMapper.toDTO(scoreModel);
    }

    @Override
    public void deleteScore(Long id) {
        ScoreModel scoreModel = scoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Score record not found with id: " + id));
        scoreRepository.delete(scoreModel);
    }
}
