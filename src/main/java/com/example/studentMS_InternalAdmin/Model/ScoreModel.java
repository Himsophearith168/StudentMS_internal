package com.example.studentMS_InternalAdmin.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scores")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Setter
@Getter
public class ScoreModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private Double score;
}
