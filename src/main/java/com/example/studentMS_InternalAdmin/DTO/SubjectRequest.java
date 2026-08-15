package com.example.studentMS_InternalAdmin.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectRequest {

    @NotBlank(message = "Subject name is required")
    private String subjectName;

    @DecimalMin(value = "0.0", message = "Max score must be greater than or equal to 0")
    @Builder.Default
    private Double maxScore = 10.00;
}
