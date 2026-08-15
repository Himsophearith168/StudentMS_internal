package com.example.studentMS_InternalAdmin.Service.impl;

import com.example.studentMS_InternalAdmin.DTO.ClassAnalyticsResponse;
import com.example.studentMS_InternalAdmin.DTO.FinalScoreResponse;
import com.example.studentMS_InternalAdmin.Execption.ResourceNotFoundException;
import com.example.studentMS_InternalAdmin.Mapper.FinalScoreMapper;
import com.example.studentMS_InternalAdmin.Model.*;
import com.example.studentMS_InternalAdmin.Repository.*;
import com.example.studentMS_InternalAdmin.Service.GradeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GradeCalculationServiceImpl implements GradeCalculationService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final SubjectRepository subjectRepository;
    private final MonthlyScoreRepository monthlyScoreRepository;
    private final SemesterScoreRepository semesterScoreRepository;
    private final FinalScoreRepository finalScoreRepository;

    @Autowired
    public GradeCalculationServiceImpl(StudentRepository studentRepository,
                                       ClassRepository classRepository,
                                       SubjectRepository subjectRepository,
                                       MonthlyScoreRepository monthlyScoreRepository,
                                       SemesterScoreRepository semesterScoreRepository,
                                       FinalScoreRepository finalScoreRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.subjectRepository = subjectRepository;
        this.monthlyScoreRepository = monthlyScoreRepository;
        this.semesterScoreRepository = semesterScoreRepository;
        this.finalScoreRepository = finalScoreRepository;
    }

    @Override
    public String evaluateGradeMention(Double score) {
        if (score == null) return "ខ្សោយ";
        double roundScore = round(score, 2);
        if (roundScore >= 9.5) {
            return "ល្អណាស់";
        } else if (roundScore >= 8.0) {
            return "ល្អ";
        } else if (roundScore >= 6.5) {
            return "ល្អបង្គួរ";
        } else if (roundScore >= 5.0) {
            return "មធ្យម";
        } else {
            return "ខ្សោយ";
        }
    }

    @Override
    @Transactional
    public FinalScoreResponse calculateStudentAnnualScore(Long studentId, String academicYear) {
        StudentModel student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));

        Double sem1Avg = calculateSemesterAverage(studentId, 1);
        Double sem2Avg = calculateSemesterAverage(studentId, 2);

        double annualAvg;
        if (sem1Avg != null && sem2Avg != null) {
            annualAvg = round((sem1Avg + sem2Avg) / 2.0, 2);
        } else if (sem1Avg != null) {
            annualAvg = round(sem1Avg, 2);
        } else if (sem2Avg != null) {
            annualAvg = round(sem2Avg, 2);
        } else {
            annualAvg = 0.0;
        }

        String mention = evaluateGradeMention(annualAvg);

        Optional<FinalScoreModel> existingOpt = finalScoreRepository.findByStudentIdAndAcademicYear(studentId, academicYear);
        FinalScoreModel model;
        if (existingOpt.isPresent()) {
            model = existingOpt.get();
            model.setSemester1Avg(sem1Avg != null ? round(sem1Avg, 2) : null);
            model.setSemester2Avg(sem2Avg != null ? round(sem2Avg, 2) : null);
            model.setAnnualAvg(annualAvg);
            model.setGradeMention(mention);
        } else {
            model = FinalScoreModel.builder()
                    .student(student)
                    .academicYear(academicYear)
                    .semester1Avg(sem1Avg != null ? round(sem1Avg, 2) : null)
                    .semester2Avg(sem2Avg != null ? round(sem2Avg, 2) : null)
                    .annualAvg(annualAvg)
                    .gradeMention(mention)
                    .build();
        }

        FinalScoreModel saved = finalScoreRepository.save(model);
        return FinalScoreMapper.toDTO(saved);
    }

    private Double calculateSemesterAverage(Long studentId, Integer semester) {
        List<SubjectModel> subjects = subjectRepository.findAll();
        if (subjects.isEmpty()) return null;

        List<Double> subjectSemesterScores = new ArrayList<>();

        for (SubjectModel subject : subjects) {
            List<MonthlyScoreModel> monthlyScores = monthlyScoreRepository
                    .findByStudentIdAndSubjectIdAndSemester(studentId, subject.getId(), semester);

            Double monthlyAvg = null;
            if (!monthlyScores.isEmpty()) {
                double sum = monthlyScores.stream().mapToDouble(MonthlyScoreModel::getScore).sum();
                monthlyAvg = sum / monthlyScores.size();
            }

            Optional<SemesterScoreModel> examOpt = semesterScoreRepository
                    .findByStudentIdAndSubjectIdAndSemester(studentId, subject.getId(), semester);

            Double examScore = examOpt.map(SemesterScoreModel::getExamScore).orElse(null);

            if (monthlyAvg != null && examScore != null) {
                double subjectScore = (monthlyAvg + examScore) / 2.0;
                subjectSemesterScores.add(subjectScore);
            } else if (monthlyAvg != null) {
                subjectSemesterScores.add(monthlyAvg);
            } else if (examScore != null) {
                subjectSemesterScores.add(examScore);
            }
        }

        if (subjectSemesterScores.isEmpty()) return null;
        double sumAll = subjectSemesterScores.stream().mapToDouble(Double::doubleValue).sum();
        return sumAll / subjectSemesterScores.size();
    }

    @Override
    @Transactional
    public List<FinalScoreResponse> calculateAndRankClass(Long classId) {
        ClassModel classModel = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + classId));

        List<StudentModel> students = studentRepository.findByClassModelId(classId);
        if (students.isEmpty()) {
            return Collections.emptyList();
        }

        String academicYear = classModel.getAcademicYear();
        List<FinalScoreModel> finalScores = new ArrayList<>();

        for (StudentModel student : students) {
            calculateStudentAnnualScore(student.getId(), academicYear);
            FinalScoreModel scoreModel = finalScoreRepository
                    .findByStudentIdAndAcademicYear(student.getId(), academicYear)
                    .orElse(null);
            if (scoreModel != null) {
                finalScores.add(scoreModel);
            }
        }

        // Dynamic ranking by annualAvg descending
        finalScores.sort(Comparator.comparing(FinalScoreModel::getAnnualAvg).reversed());

        int rank = 1;
        for (int i = 0; i < finalScores.size(); i++) {
            if (i > 0 && !finalScores.get(i).getAnnualAvg().equals(finalScores.get(i - 1).getAnnualAvg())) {
                rank = i + 1;
            }
            finalScores.get(i).setClassRank(rank);
        }

        List<FinalScoreModel> updatedList = finalScoreRepository.saveAll(finalScores);
        return updatedList.stream().map(FinalScoreMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<FinalScoreResponse> getClassFinalScores(Long classId) {
        ClassModel classModel = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + classId));

        List<StudentModel> students = studentRepository.findByClassModelId(classId);
        List<Long> studentIds = students.stream().map(StudentModel::getId).collect(Collectors.toList());

        List<FinalScoreModel> scores = finalScoreRepository.findByStudentIdInAndAcademicYear(studentIds, classModel.getAcademicYear());
        scores.sort(Comparator.comparing(FinalScoreModel::getClassRank, Comparator.nullsLast(Comparator.naturalOrder())));

        return scores.stream().map(FinalScoreMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ClassAnalyticsResponse getClassAnalytics(Long classId) {
        ClassModel classModel = classRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with id: " + classId));

        List<StudentModel> students = studentRepository.findByClassModelId(classId);
        int totalRoster = students.size();
        if (totalRoster == 0) {
            return ClassAnalyticsResponse.builder()
                    .classId(classId)
                    .className(classModel.getClassName())
                    .academicYear(classModel.getAcademicYear())
                    .teacherName(classModel.getTeacher() != null ? classModel.getTeacher().getFullName() : "N/A")
                    .totalEvaluatedRoster(0)
                    .build();
        }

        int femaleCount = (int) students.stream().filter(s -> "F".equalsIgnoreCase(s.getGender())).count();
        int maleCount = totalRoster - femaleCount;
        double femalePct = round((double) femaleCount * 100.0 / totalRoster, 1);
        double malePct = round((double) maleCount * 100.0 / totalRoster, 1);

        List<Long> studentIds = students.stream().map(StudentModel::getId).collect(Collectors.toList());
        List<FinalScoreModel> finalScores = finalScoreRepository.findByStudentIdInAndAcademicYear(studentIds, classModel.getAcademicYear());

        List<Double> scoresList = finalScores.stream().map(FinalScoreModel::getAnnualAvg).collect(Collectors.toList());
        if (scoresList.isEmpty()) {
            return ClassAnalyticsResponse.builder()
                    .classId(classId)
                    .className(classModel.getClassName())
                    .academicYear(classModel.getAcademicYear())
                    .teacherName(classModel.getTeacher() != null ? classModel.getTeacher().getFullName() : "N/A")
                    .totalEvaluatedRoster(totalRoster)
                    .femaleCount(femaleCount)
                    .femalePercentage(femalePct)
                    .maleCount(maleCount)
                    .malePercentage(malePct)
                    .build();
        }

        double mean = scoresList.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double min = scoresList.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        double max = scoresList.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);

        double variance = scoresList.stream()
                .mapToDouble(score -> Math.pow(score - mean, 2))
                .average()
                .orElse(0.0);
        double stdDev = Math.sqrt(variance);

        int passedCount = (int) scoresList.stream().filter(s -> s >= 5.0).count();
        int failedCount = totalRoster - passedCount;
        double passRate = round((double) passedCount * 100.0 / totalRoster, 1);

        return ClassAnalyticsResponse.builder()
                .classId(classId)
                .className(classModel.getClassName())
                .academicYear(classModel.getAcademicYear())
                .teacherName(classModel.getTeacher() != null ? classModel.getTeacher().getFullName() : "N/A")
                .totalEvaluatedRoster(totalRoster)
                .femaleCount(femaleCount)
                .femalePercentage(femalePct)
                .maleCount(maleCount)
                .malePercentage(malePct)
                .classMonthlyMeanScore(round(mean, 2))
                .scoreStandardDeviation(round(stdDev, 2))
                .minimumScore(round(min, 2))
                .maximumScore(round(max, 2))
                .overallPassRate(passRate)
                .passedCount(passedCount)
                .failedCount(failedCount)
                .build();
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
