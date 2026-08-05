package com.example.studentMS_InternalAdmin.Controller;

import com.example.studentMS_InternalAdmin.DTO.ScoreRequest;
import com.example.studentMS_InternalAdmin.DTO.ScoreResponse;
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

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<ScoreResponse>> recordScore(@Valid @RequestBody ScoreRequest request) {
        ScoreResponse response = scoreService.recordScore(request);
        APIResponse<ScoreResponse> apiResponse = APIResponse.<ScoreResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Score recorded successfully")
                .data(response)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ScoreResponse>>> getAllScores() {
        List<ScoreResponse> responses = scoreService.getAllScores();
        APIResponse<List<ScoreResponse>> apiResponse = APIResponse.<List<ScoreResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Scores retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ScoreResponse>> getScoreById(@PathVariable Long id) {
        ScoreResponse response = scoreService.getScore(id);
        APIResponse<ScoreResponse> apiResponse = APIResponse.<ScoreResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Score retrieved successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<APIResponse<List<ScoreResponse>>> getScoresByStudent(@PathVariable Long studentId) {
        List<ScoreResponse> responses = scoreService.getScoresByStudent(studentId);
        APIResponse<List<ScoreResponse>> apiResponse = APIResponse.<List<ScoreResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Student scores retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<APIResponse<List<ScoreResponse>>> getScoresBySubject(@PathVariable Long subjectId) {
        List<ScoreResponse> responses = scoreService.getScoresBySubject(subjectId);
        APIResponse<List<ScoreResponse>> apiResponse = APIResponse.<List<ScoreResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Subject scores retrieved successfully")
                .data(responses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ScoreResponse>> updateScore(
            @PathVariable Long id,
            @Valid @RequestBody ScoreRequest request) {
        ScoreResponse response = scoreService.updateScore(id, request);
        APIResponse<ScoreResponse> apiResponse = APIResponse.<ScoreResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Score updated successfully")
                .data(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteScore(@PathVariable Long id) {
        scoreService.deleteScore(id);
        APIResponse<Void> apiResponse = APIResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Score deleted successfully")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
