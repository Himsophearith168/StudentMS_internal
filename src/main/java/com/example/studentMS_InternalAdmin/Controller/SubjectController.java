package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.SubjectRequest;
import com.example.studentMS_InternalAdmin.DTO.SubjectResponse;
import com.example.studentMS_InternalAdmin.Service.SubjectService;
import com.example.studentMS_InternalAdmin.Util.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<SubjectResponse>> createSubject(@Valid @RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.createSubject(request);
        APIResponse<SubjectResponse> apiResponse = APIResponse.<SubjectResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Subject created successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<SubjectResponse>>> getAllSubjects() {
        List<SubjectResponse> responses = subjectService.getSubjects();
        APIResponse<List<SubjectResponse>> apiResponse = APIResponse.<List<SubjectResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Subjects retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SubjectResponse>> getSubjectById(@PathVariable Long id) {
        SubjectResponse response = subjectService.getSubject(id);
        APIResponse<SubjectResponse> apiResponse = APIResponse.<SubjectResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Subject retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<SubjectResponse>> updateSubject(@PathVariable Long id, @Valid @RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.updateSubject(id, request);
        APIResponse<SubjectResponse> apiResponse = APIResponse.<SubjectResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Subject updated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Subject deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
