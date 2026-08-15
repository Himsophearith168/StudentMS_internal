package com.example.studentMS_InternalAdmin.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassRequest {
    @NotBlank(message = "Class name is required")
    private String className;

    @NotBlank(message = "Academic year is required")
    private String academicYear;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;
}
