package com.example.studentMS_InternalAdmin;

import com.example.studentMS_InternalAdmin.DTO.ClassRequest;
import com.example.studentMS_InternalAdmin.Model.AdminModel;
import com.example.studentMS_InternalAdmin.Model.ClassModel;
import com.example.studentMS_InternalAdmin.Repository.AdminRepository;
import com.example.studentMS_InternalAdmin.Repository.ClassRepository;
import com.example.studentMS_InternalAdmin.Service.impl.ClassServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SingleTeacherOwnershipTest {

    @Mock
    private ClassRepository classRepository;

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private ClassServiceimpl classService;

    private AdminModel teacher;

    @BeforeEach
    void setUp() {
        teacher = AdminModel.builder()
                .id(1L)
                .username("teacher1")
                .fullName("Teacher Sokha")
                .role("TEACHER")
                .build();
    }

    @Test
    void testSingleTeacherClassOwnership_Success() {
        ClassRequest request = ClassRequest.builder()
                .className("Grade 10A")
                .academicYear("2025-2026")
                .teacherId(1L)
                .build();

        when(adminRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(classRepository.existsByTeacherIdAndAcademicYear(1L, "2025-2026")).thenReturn(false);

        ClassModel savedClass = ClassModel.builder()
                .id(10L)
                .className("Grade 10A")
                .academicYear("2025-2026")
                .teacher(teacher)
                .build();
        when(classRepository.save(any())).thenReturn(savedClass);

        var response = classService.createClass(request);
        assertNotNull(response);
        assertEquals("Grade 10A", response.getClassName());
        assertEquals("Teacher Sokha", response.getTeacherName());
    }

    @Test
    void testSingleTeacherClassOwnership_DuplicateTeacherInSameYear_ThrowsException() {
        ClassRequest request = ClassRequest.builder()
                .className("Grade 10B")
                .academicYear("2025-2026")
                .teacherId(1L)
                .build();

        when(adminRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(classRepository.existsByTeacherIdAndAcademicYear(1L, "2025-2026")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            classService.createClass(request);
        });

        assertTrue(exception.getMessage().contains("is already assigned as head teacher to another class"));
        verify(classRepository, never()).save(any());
    }
}
