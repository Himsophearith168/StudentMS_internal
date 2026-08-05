package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.StudentCreateRequest;
import com.example.studentMS_InternalAdmin.DTO.StudentResponse;
import com.example.studentMS_InternalAdmin.DTO.StudentUpdateRequest;
import com.example.studentMS_InternalAdmin.Service.StudentService;
import com.example.studentMS_InternalAdmin.Util.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<StudentResponse>> createStudent(@Valid @RequestBody StudentCreateRequest request) {
        StudentResponse response = studentService.createStudent(request);
        APIResponse<StudentResponse> apiResponse = APIResponse.<StudentResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Student account created successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<StudentResponse>>> getAllStudents() {
        List<StudentResponse> responses = studentService.getStudents();
        APIResponse<List<StudentResponse>> apiResponse = APIResponse.<List<StudentResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Students retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<StudentResponse>> getStudentById(@PathVariable Long id) {
        StudentResponse response = studentService.getStudent(id);
        APIResponse<StudentResponse> apiResponse = APIResponse.<StudentResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Student retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<StudentResponse>> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentUpdateRequest request) {
        StudentResponse response = studentService.updateStudent(id, request);
        APIResponse<StudentResponse> apiResponse = APIResponse.<StudentResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Student updated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/{id}/enroll-subjects")
    public ResponseEntity<APIResponse<StudentResponse>> enrollSubjects(@PathVariable Long id, @RequestBody Set<Long> subjectIds) {
        StudentResponse response = studentService.enrollSubjects(id, subjectIds);
        APIResponse<StudentResponse> apiResponse = APIResponse.<StudentResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Subjects enrolled successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Student deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
