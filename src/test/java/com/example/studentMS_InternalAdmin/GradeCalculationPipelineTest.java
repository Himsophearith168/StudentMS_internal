package com.example.studentMS_InternalAdmin;

import com.example.studentMS_InternalAdmin.Service.impl.GradeCalculationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeCalculationPipelineTest {

    private GradeCalculationServiceImpl gradeCalculationService;

    @BeforeEach
    void setUp() {
        gradeCalculationService = new GradeCalculationServiceImpl(null, null, null, null, null, null);
    }

    @Test
    void testCambodianGradeMentions() {
        assertEquals("ល្អណាស់", gradeCalculationService.evaluateGradeMention(9.75));
        assertEquals("ល្អណាស់", gradeCalculationService.evaluateGradeMention(9.50));
        assertEquals("ល្អ", gradeCalculationService.evaluateGradeMention(9.40));
        assertEquals("ល្អ", gradeCalculationService.evaluateGradeMention(8.00));
        assertEquals("ល្អបង្គួរ", gradeCalculationService.evaluateGradeMention(7.90));
        assertEquals("ល្អបង្គួរ", gradeCalculationService.evaluateGradeMention(6.50));
        assertEquals("មធ្យម", gradeCalculationService.evaluateGradeMention(6.40));
        assertEquals("មធ្យម", gradeCalculationService.evaluateGradeMention(5.00));
        assertEquals("ខ្សោយ", gradeCalculationService.evaluateGradeMention(4.99));
        assertEquals("ខ្សោយ", gradeCalculationService.evaluateGradeMention(3.20));
    }
}
