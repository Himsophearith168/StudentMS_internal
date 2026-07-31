package com.example.studentMS_InternalAdmin.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classID",nullable = false,unique = true)
    private ClassModel classid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectID",nullable = false,unique = true)
    private SubjectModel subjectid;

}
