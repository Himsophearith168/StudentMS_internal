package com.example.studentMS_InternalAdmin.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"student", "subject"})
@ToString(exclude = {"student", "subject"})
public class ScoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentModel student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectModel subject;

    @Builder.Default
    private Double quiz = 0.0;

    @Builder.Default
    private Double assignment = 0.0;

    @Builder.Default
    private Double midterm = 0.0;

    @Column(name = "final_exam")
    @Builder.Default
    private Double finalExam = 0.0;

    @Builder.Default
    private Double attendance = 0.0;

    @Column(name = "total_score")
    private Double totalScore;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void calculateTotalScore() {
        double q = quiz != null ? quiz : 0.0;
        double a = assignment != null ? assignment : 0.0;
        double m = midterm != null ? midterm : 0.0;
        double f = finalExam != null ? finalExam : 0.0;
        double att = attendance != null ? attendance : 0.0;
        this.totalScore = q + a + m + f + att;
    }
}
