package com.example.studentMS_InternalAdmin.Config;

import com.example.studentMS_InternalAdmin.Model.*;
import com.example.studentMS_InternalAdmin.Repository.*;
import com.example.studentMS_InternalAdmin.Service.GradeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final MonthlyScoreRepository monthlyScoreRepository;
    private final SemesterScoreRepository semesterScoreRepository;
    private final GradeCalculationService gradeCalculationService;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataInitializer(AdminRepository adminRepository,
                           ClassRepository classRepository,
                           StudentRepository studentRepository,
                           SubjectRepository subjectRepository,
                           MonthlyScoreRepository monthlyScoreRepository,
                           SemesterScoreRepository semesterScoreRepository,
                           GradeCalculationService gradeCalculationService,
                           PasswordEncoder passwordEncoder,
                           JdbcTemplate jdbcTemplate) {
        this.adminRepository = adminRepository;
        this.classRepository = classRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.monthlyScoreRepository = monthlyScoreRepository;
        this.semesterScoreRepository = semesterScoreRepository;
        this.gradeCalculationService = gradeCalculationService;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            // Precise Legacy Column Detection and Cleanup
            try {
                jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
                jdbcTemplate.execute("DROP TABLE IF EXISTS scores");

                try {
                    jdbcTemplate.execute("SELECT first_name FROM students LIMIT 1");
                    jdbcTemplate.execute("DROP TABLE IF EXISTS permissions, final_scores_for_year, semester_scores, monthly_scores, students");
                } catch (Exception ignored) {}

                try {
                    jdbcTemplate.execute("SELECT id FROM admins LIMIT 1");
                    jdbcTemplate.execute("DROP TABLE IF EXISTS admins");
                } catch (Exception ignored) {}

                try {
                    jdbcTemplate.execute("SELECT classid FROM classes LIMIT 1");
                    jdbcTemplate.execute("DROP TABLE IF EXISTS classes");
                } catch (Exception ignored) {}

                try {
                    jdbcTemplate.execute("SELECT subjectid FROM subjects LIMIT 1");
                    jdbcTemplate.execute("DROP TABLE IF EXISTS subjects");
                } catch (Exception ignored) {}

                jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
            } catch (Exception ignored) {}

            if (!adminRepository.existsByUsername("admin")) {
                AdminModel admin = AdminModel.builder()
                        .username("admin")
                        .passwordHash(passwordEncoder.encode("admin123"))
                        .fullName("System Administrator")
                        .email("admin@school.com")
                        .role("ADMIN")
                        .build();
                adminRepository.save(admin);
            }

            if (!adminRepository.existsByUsername("teacher1")) {
                AdminModel teacher = AdminModel.builder()
                        .username("teacher1")
                        .passwordHash(passwordEncoder.encode("teacher123"))
                        .fullName("Teacher Sokha")
                        .email("sokha@school.edu.kh")
                        .role("TEACHER")
                        .build();
                AdminModel savedTeacher = adminRepository.save(teacher);

                // Seed Subjects safely with existence check
                SubjectModel khmer = getOrCreateSubject("Khmer", 10.00);
                SubjectModel math = getOrCreateSubject("Mathematics", 10.00);
                SubjectModel physics = getOrCreateSubject("Physics", 10.00);

                // Seed Class section safely with existence check
                ClassModel grade10A = getOrCreateClass("Grade 10A", "2025-2026", savedTeacher);

                // Seed 40 Students (21 Female, 19 Male) matching empirical benchmark
                List<StudentModel> studentsList = new ArrayList<>();
                Random random = new Random(42);

                for (int i = 1; i <= 40; i++) {
                    final int rollNo = i;
                    final String gender = (rollNo <= 21) ? "F" : "M";
                    final String code = String.format("STU%03d", rollNo);
                    final String name = gender.equals("F") ? "Srey " + (char)('A' + (rollNo % 26)) + " - " + rollNo : "Sok " + (char)('A' + (rollNo % 26)) + " - " + rollNo;

                    Optional<StudentModel> existingOpt = studentRepository.findByStudentCode(code);
                    StudentModel student;
                    if (existingOpt.isPresent()) {
                        student = existingOpt.get();
                    } else {
                        student = studentRepository.save(StudentModel.builder()
                                .studentCode(code)
                                .fullName(name)
                                .gender(gender)
                                .rollNumber(rollNo)
                                .classModel(grade10A)
                                .build());
                    }
                    studentsList.add(student);
                }

                // Seed Scores (Semester 1 & 2 Monthly + Exam Scores)
                List<SubjectModel> subjects = Arrays.asList(khmer, math, physics);
                String[] monthsSem1 = {"Nov", "Dec", "Jan"};
                String[] monthsSem2 = {"Mar", "Apr", "May"};

                for (StudentModel s : studentsList) {
                    for (SubjectModel subj : subjects) {
                        double base = (s.getRollNumber() == 40) ? 4.5 : 7.2;

                        for (String m : monthsSem1) {
                            double score = Math.min(10.0, Math.max(3.0, base + (random.nextDouble() * 2.4 - 1.2)));
                            monthlyScoreRepository.save(MonthlyScoreModel.builder()
                                    .student(s)
                                    .subject(subj)
                                    .semester(1)
                                    .monthName(m)
                                    .score(Math.round(score * 100.0) / 100.0)
                                    .build());
                        }

                        double exam1 = Math.min(10.0, Math.max(3.0, base + (random.nextDouble() * 2.0 - 1.0)));
                        semesterScoreRepository.save(SemesterScoreModel.builder()
                                .student(s)
                                .subject(subj)
                                .semester(1)
                                .examScore(Math.round(exam1 * 100.0) / 100.0)
                                .build());

                        for (String m : monthsSem2) {
                            double score = Math.min(10.0, Math.max(3.0, base + (random.nextDouble() * 2.4 - 1.2)));
                            monthlyScoreRepository.save(MonthlyScoreModel.builder()
                                    .student(s)
                                    .subject(subj)
                                    .semester(2)
                                    .monthName(m)
                                    .score(Math.round(score * 100.0) / 100.0)
                                    .build());
                        }

                        double exam2 = Math.min(10.0, Math.max(3.0, base + (random.nextDouble() * 2.0 - 1.0)));
                        semesterScoreRepository.save(SemesterScoreModel.builder()
                                .student(s)
                                .subject(subj)
                                .semester(2)
                                .examScore(Math.round(exam2 * 100.0) / 100.0)
                                .build());
                    }
                }

                // Auto-calculate final annual scores & dynamic class ranks
                gradeCalculationService.calculateAndRankClass(grade10A.getId());

                System.out.println("=================================================");
                System.out.println(">>> SEEDED BENCHMARK DEMO DATA SUCCESSFULLY");
                System.out.println(">>> Head Teacher : teacher1 / teacher123");
                System.out.println(">>> Class        : Grade 10A (2025-2026)");
                System.out.println(">>> Roster       : 40 Students (21 Female, 19 Male)");
                System.out.println("=================================================");
            }
        } catch (Exception e) {
            System.err.println(">>> ERROR in DataInitializer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private SubjectModel getOrCreateSubject(String name, Double maxScore) {
        return subjectRepository.findBySubjectName(name)
                .orElseGet(() -> subjectRepository.save(SubjectModel.builder()
                        .subjectName(name)
                        .maxScore(maxScore)
                        .build()));
    }

    private ClassModel getOrCreateClass(String className, String academicYear, AdminModel teacher) {
        return classRepository.findByClassName(className)
                .orElseGet(() -> classRepository.save(ClassModel.builder()
                        .className(className)
                        .academicYear(academicYear)
                        .teacher(teacher)
                        .build()));
    }
}
