package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.SubjectRequest;
import com.example.studentMS_InternalAdmin.DTO.SubjectResponse;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.SubjectMapper;
import com.example.studentMS_InternalAdmin.Model.SubjectModel;
import com.example.studentMS_InternalAdmin.Repository.SubjectRepository;
import com.example.studentMS_InternalAdmin.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public SubjectResponse createSubject(SubjectRequest request) {
        if (subjectRepository.existsBySubjectName(request.getSubjectName())) {
            throw new IllegalArgumentException("Subject name already exists: " + request.getSubjectName());
        }
        SubjectModel subjectModel = SubjectMapper.toEntity(request);
        SubjectModel saved = subjectRepository.save(subjectModel);
        return SubjectMapper.toDTO(saved);
    }

    @Override
    public SubjectResponse updateSubject(Long id, SubjectRequest request) {
        SubjectModel existing = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));

        if (!existing.getSubjectName().equals(request.getSubjectName()) &&
                subjectRepository.existsBySubjectName(request.getSubjectName())) {
            throw new IllegalArgumentException("Subject name already exists: " + request.getSubjectName());
        }

        existing.setSubjectName(request.getSubjectName());
        existing.setSubjectDescription(request.getSubjectDescription());
        existing.setCredit(request.getCredit());
        existing.setTeacher(request.getTeacher());
        existing.setSemester(request.getSemester());
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }

        SubjectModel updated = subjectRepository.save(existing);
        return SubjectMapper.toDTO(updated);
    }

    @Override
    public List<SubjectResponse> getSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectResponse getSubject(Long id) {
        SubjectModel subjectModel = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        return SubjectMapper.toDTO(subjectModel);
    }

    @Override
    public void deleteSubject(Long id) {
        SubjectModel subjectModel = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        subjectRepository.delete(subjectModel);
    }
}
