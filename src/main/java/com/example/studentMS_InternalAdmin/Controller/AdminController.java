package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.AdminRequest;
import com.example.studentMS_InternalAdmin.DTO.AdminResponse;
import com.example.studentMS_InternalAdmin.Service.AdminService;
import com.example.studentMS_InternalAdmin.Util.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<AdminResponse>> createAdmin(@Valid @RequestBody AdminRequest request) {
        AdminResponse response = adminService.createAdmin(request);
        APIResponse<AdminResponse> apiResponse = APIResponse.<AdminResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Staff account created successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<AdminResponse>>> getAllAdmins() {
        List<AdminResponse> responses = adminService.getAllAdmins();
        APIResponse<List<AdminResponse>> apiResponse = APIResponse.<List<AdminResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Staff accounts retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<AdminResponse>> getAdminById(@PathVariable Long id) {
        AdminResponse response = adminService.getAdminById(id);
        APIResponse<AdminResponse> apiResponse = APIResponse.<AdminResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Staff account retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<AdminResponse>> updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminRequest request) {
        AdminResponse response = adminService.updateAdmin(id, request);
        APIResponse<AdminResponse> apiResponse = APIResponse.<AdminResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Staff account updated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Staff account deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
