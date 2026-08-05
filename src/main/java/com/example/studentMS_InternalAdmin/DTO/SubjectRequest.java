package com.example.studentMS_InternalAdmin.DTO;

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

    @NotBlank(message = "The Semester to Study is Require!!")
    private String semester;

}
