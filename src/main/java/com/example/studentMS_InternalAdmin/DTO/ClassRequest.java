package com.example.studentMS_InternalAdmin.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassRequest {
    @NotBlank(message = "Class Name is Require!!")
    private String className;
    @NotBlank(message = "The Academic Year to Study is Require!!")
    private String academicYear;
    @NotBlank(message = "The Semester to Study is Require!!")
    private String semester;
    private String description;
    private Integer maxStudents;

}
