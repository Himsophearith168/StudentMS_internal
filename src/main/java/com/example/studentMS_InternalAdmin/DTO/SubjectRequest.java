package com.example.studentMS_InternalAdmin.DTO;

import jakarta.validation.constraints.Min;
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

    private String subjectDescription;

    @Min(value = 1, message = "Credit must be at least 1")
    private Integer credit;

    private String teacher;

    private String semester;

    private String status;
}
