package com.example.sis;

import com.example.sis.entity.Course;
import com.example.sis.entity.Enrollment;
import com.example.sis.entity.Student;
import com.example.sis.repository.CourseRepository;
import com.example.sis.repository.EnrollmentRepository;
import com.example.sis.repository.StudentRepository;
import com.example.sis.service.EnrollmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Student student;
    private Course course;
    private Enrollment enrollment;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("Ali");
        student.setLastName("Hasanov");
        student.setGpa(0.0);

        course = new Course();
        course.setId(1L);
        course.setCapacity(30);

        enrollment = new Enrollment();
        enrollment.setId(1L);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
    }

    @Test
    void score91_shouldReturn_A() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any())).thenReturn(enrollment);
        when(enrollmentRepository.findByStudentId(1L)).thenReturn(List.of(enrollment));
        when(studentRepository.save(any())).thenReturn(student);

        Enrollment result = enrollmentService.assignGrade(1L, 91.0);

        assertEquals("A", result.getLetterGrade());
        assertFalse(result.getIsFailed());
    }

    @Test
    void score85_shouldReturn_B() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any())).thenReturn(enrollment);
        when(enrollmentRepository.findByStudentId(1L)).thenReturn(List.of(enrollment));
        when(studentRepository.save(any())).thenReturn(student);

        Enrollment result = enrollmentService.assignGrade(1L, 85.0);

        assertEquals("B", result.getLetterGrade());
        assertFalse(result.getIsFailed());
    }

    @Test
    void score75_shouldReturn_C() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any())).thenReturn(enrollment);
        when(enrollmentRepository.findByStudentId(1L)).thenReturn(List.of(enrollment));
        when(studentRepository.save(any())).thenReturn(student);

        Enrollment result = enrollmentService.assignGrade(1L, 75.0);

        assertEquals("C", result.getLetterGrade());
        assertFalse(result.getIsFailed());
    }

    @Test
    void score55_shouldReturn_D() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any())).thenReturn(enrollment);
        when(enrollmentRepository.findByStudentId(1L)).thenReturn(List.of(enrollment));
        when(studentRepository.save(any())).thenReturn(student);

        Enrollment result = enrollmentService.assignGrade(1L, 55.0);

        assertEquals("D", result.getLetterGrade());
        assertFalse(result.getIsFailed());
    }

    @Test
    void score40_shouldReturn_F() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any())).thenReturn(enrollment);
        when(enrollmentRepository.findByStudentId(1L)).thenReturn(List.of(enrollment));
        when(studentRepository.save(any())).thenReturn(student);

        Enrollment result = enrollmentService.assignGrade(1L, 40.0);

        assertEquals("F", result.getLetterGrade());
        assertTrue(result.getIsFailed());
    }

    @Test
    void fullCourse_shouldThrowException() {
        course.setCapacity(1);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(enrollmentRepository.countByCourseId(1L)).thenReturn(1);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> enrollmentService.enrollStudentToCourse(1L, 1L));

        assertEquals("Course is full", ex.getMessage());
    }

    @Test
    void gpa_shouldBeCalculatedCorrectly() {
        enrollment.setScore(80.0);
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment));
        when(enrollmentRepository.save(any())).thenReturn(enrollment);
        when(enrollmentRepository.findByStudentId(1L)).thenReturn(List.of(enrollment));
        when(studentRepository.save(any())).thenReturn(student);

        enrollmentService.assignGrade(1L, 80.0);

        assertEquals(80.0, student.getGpa());
    }
}
