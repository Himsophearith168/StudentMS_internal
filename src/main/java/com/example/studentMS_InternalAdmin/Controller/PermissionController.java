package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.PermissionRequest;
import com.example.studentMS_InternalAdmin.DTO.PermissionResponse;
import com.example.studentMS_InternalAdmin.DTO.PermissionStatusUpdateRequest;
import com.example.studentMS_InternalAdmin.Service.PermissionService;
import com.example.studentMS_InternalAdmin.Util.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<PermissionResponse>> requestPermission(@Valid @RequestBody PermissionRequest request) {
        PermissionResponse response = permissionService.requestPermission(request);
        APIResponse<PermissionResponse> apiResponse = APIResponse.<PermissionResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Permission request submitted successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<PermissionResponse>>> getAllPermissions() {
        List<PermissionResponse> responses = permissionService.getAllPermissions();
        APIResponse<List<PermissionResponse>> apiResponse = APIResponse.<List<PermissionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Permission requests retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<PermissionResponse>> getPermissionById(@PathVariable Long id) {
        PermissionResponse response = permissionService.getPermission(id);
        APIResponse<PermissionResponse> apiResponse = APIResponse.<PermissionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Permission request retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<APIResponse<List<PermissionResponse>>> getPermissionsByStudent(@PathVariable Long studentId) {
        List<PermissionResponse> responses = permissionService.getPermissionsByStudent(studentId);
        APIResponse<List<PermissionResponse>> apiResponse = APIResponse.<List<PermissionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Student permission requests retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<APIResponse<PermissionResponse>> updatePermissionStatus(
            @PathVariable Long id,
            @Valid @RequestBody PermissionStatusUpdateRequest request) {
        PermissionResponse response = permissionService.updatePermissionStatus(id, request);
        APIResponse<PermissionResponse> apiResponse = APIResponse.<PermissionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Permission request status updated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
