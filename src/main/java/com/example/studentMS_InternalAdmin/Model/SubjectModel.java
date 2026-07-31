package com.example.studentMS_InternalAdmin.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Subjects")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Setter
@Getter
public class SubjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectid;
    @Column(nullable = false, unique = true)
    private String SubjectName;
    private String SubjectDescription;
}
