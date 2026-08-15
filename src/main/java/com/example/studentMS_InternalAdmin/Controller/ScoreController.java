package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.*;
import com.example.studentMS_InternalAdmin.Service.GradeCalculationService;
import com.example.studentMS_InternalAdmin.Service.ScoreService;
import com.example.studentMS_InternalAdmin.Util.APIResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/scores")
public class ScoreController {

    private final ScoreService scoreService;
    private final GradeCalculationService gradeCalculationService;

    @Autowired
    public ScoreController(ScoreService scoreService, GradeCalculationService gradeCalculationService) {
        this.scoreService = scoreService;
        this.gradeCalculationService = gradeCalculationService;
    }

    @PostMapping("/monthly")
    public ResponseEntity<APIResponse<MonthlyScoreResponse>> recordMonthlyScore(@Valid @RequestBody MonthlyScoreRequest request) {
        MonthlyScoreResponse response = scoreService.recordMonthlyScore(request);
        APIResponse<MonthlyScoreResponse> apiResponse = APIResponse.<MonthlyScoreResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Monthly score recorded successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/monthly/student/{studentId}")
    public ResponseEntity<APIResponse<List<MonthlyScoreResponse>>> getMonthlyScoresByStudent(
            @PathVariable Long studentId,
            @RequestParam(required = false) Integer semester) {
        List<MonthlyScoreResponse> responses;
        if (semester != null) {
            responses = scoreService.getMonthlyScoresByStudentAndSemester(studentId, semester);
        } else {
            responses = scoreService.getMonthlyScoresByStudent(studentId);
        }
        APIResponse<List<MonthlyScoreResponse>> apiResponse = APIResponse.<List<MonthlyScoreResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Monthly scores retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/semester")
    public ResponseEntity<APIResponse<SemesterScoreResponse>> recordSemesterScore(@Valid @RequestBody SemesterScoreRequest request) {
        SemesterScoreResponse response = scoreService.recordSemesterScore(request);
        APIResponse<SemesterScoreResponse> apiResponse = APIResponse.<SemesterScoreResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Semester exam score recorded successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/semester/student/{studentId}")
    public ResponseEntity<APIResponse<List<SemesterScoreResponse>>> getSemesterScoresByStudent(
            @PathVariable Long studentId,
            @RequestParam(required = false) Integer semester) {
        List<SemesterScoreResponse> responses;
        if (semester != null) {
            responses = scoreService.getSemesterScoresByStudentAndSemester(studentId, semester);
        } else {
            responses = scoreService.getSemesterScoresByStudent(studentId);
        }
        APIResponse<List<SemesterScoreResponse>> apiResponse = APIResponse.<List<SemesterScoreResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Semester exam scores retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/final/calculate/{classId}")
    public ResponseEntity<APIResponse<List<FinalScoreResponse>>> calculateAndRankClass(@PathVariable Long classId) {
        List<FinalScoreResponse> responses = gradeCalculationService.calculateAndRankClass(classId);
        APIResponse<List<FinalScoreResponse>> apiResponse = APIResponse.<List<FinalScoreResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Annual final scores, grade mentions, and dynamic class ranks calculated successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/final/class/{classId}")
    public ResponseEntity<APIResponse<List<FinalScoreResponse>>> getClassFinalScores(@PathVariable Long classId) {
        List<FinalScoreResponse> responses = gradeCalculationService.getClassFinalScores(classId);
        APIResponse<List<FinalScoreResponse>> apiResponse = APIResponse.<List<FinalScoreResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Class final scores and rankings retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
