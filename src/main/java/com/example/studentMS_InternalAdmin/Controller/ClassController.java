package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.ClassRequest;
import com.example.studentMS_InternalAdmin.DTO.ClassResponse;
import com.example.studentMS_InternalAdmin.Service.ClassService;
import com.example.studentMS_InternalAdmin.Util.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classes")
public class ClassController {

    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<ClassResponse>> createClass(@Valid @RequestBody ClassRequest request) {
        ClassResponse response = classService.createClass(request);
        APIResponse<ClassResponse> apiResponse = APIResponse.<ClassResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Class created successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ClassResponse>>> getAllClasses() {
        List<ClassResponse> responses = classService.getClasses();
        APIResponse<List<ClassResponse>> apiResponse = APIResponse.<List<ClassResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Classes retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ClassResponse>> getClassById(@PathVariable Long id) {
        ClassResponse response = classService.getClass(id);
        APIResponse<ClassResponse> apiResponse = APIResponse.<ClassResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Class retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ClassResponse>> updateClass(@PathVariable Long id, @Valid @RequestBody ClassRequest request) {
        ClassResponse response = classService.updateClass(id, request);
        APIResponse<ClassResponse> apiResponse = APIResponse.<ClassResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Class updated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Class deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
