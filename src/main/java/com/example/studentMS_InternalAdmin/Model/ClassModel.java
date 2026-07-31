package com.example.studentMS_InternalAdmin.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Classes")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClassModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classid;
    @Column(nullable = false, unique = true)
    private String ClassName;
    private String Decription;
}
