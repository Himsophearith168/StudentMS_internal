package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.ClassAnalyticsResponse;
import com.example.studentMS_InternalAdmin.Service.GradeCalculationService;
import com.example.studentMS_InternalAdmin.Util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final GradeCalculationService gradeCalculationService;

    @Autowired
    public AnalyticsController(GradeCalculationService gradeCalculationService) {
        this.gradeCalculationService = gradeCalculationService;
    }

    @GetMapping("/class-summary/{classId}")
    public ResponseEntity<APIResponse<ClassAnalyticsResponse>> getClassAnalyticsSummary(@PathVariable Long classId) {
        ClassAnalyticsResponse response = gradeCalculationService.getClassAnalytics(classId);
        APIResponse<ClassAnalyticsResponse> apiResponse = APIResponse.<ClassAnalyticsResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Class analytics summary report generated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
