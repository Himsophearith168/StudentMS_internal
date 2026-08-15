package com.example.studentMS_InternalAdmin.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "final_scores_for_year")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"student"})
@ToString(exclude = {"student"})
public class FinalScoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "final_score_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentModel student;

    @Column(name = "academic_year", nullable = false, length = 20)
    private String academicYear;

    @Column(name = "semester_1_avg", columnDefinition = "DECIMAL(5,2)")
    private Double semester1Avg;

    @Column(name = "semester_2_avg", columnDefinition = "DECIMAL(5,2)")
    private Double semester2Avg;

    @Column(name = "annual_avg", columnDefinition = "DECIMAL(5,2)", nullable = false)
    private Double annualAvg;

    @Column(name = "class_rank")
    private Integer classRank;

    @Column(name = "grade_mention", length = 30)
    private String gradeMention; // 'ល្អណាស់', 'ល្អ', 'ល្អបង្គួរ', 'មធ្យម', 'ខ្សោយ'

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
