package com.example.studentMS_InternalAdmin.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCreateRequest {

    @NotBlank(message = "Student code is required")
    private String studentCode;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(M|F)$", message = "Gender must be 'M' or 'F'")
    private String gender;

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Roll number is required")
    private Integer rollNumber;
}
