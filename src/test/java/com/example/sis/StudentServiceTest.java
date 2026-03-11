package com.example.sis;

import com.example.sis.entity.Student;
import com.example.sis.repository.EnrollmentRepository;
import com.example.sis.repository.StudentRepository;
import com.example.sis.service.StudentService;
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
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setFirstName("Ali");
        student.setLastName("Hasanov");
        student.setEmail("ali@gmail.com");
        student.setEnrollmentYear(2024);
        student.setTuitionFee(3000.0);
        student.setPaidAmount(1500.0);
        student.setGpa(0.0);
    }

    @Test
    void addStudent_shouldReturnSavedStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.addStudent(student);

        assertEquals("Ali", result.getFirstName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void getAllStudents_shouldReturnList() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<Student> result = studentService.getAllStudents();

        assertEquals(1, result.size());
        assertEquals("Ali", result.get(0).getFirstName());
    }

    @Test
    void getStudentById_shouldReturnStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Hasanov", result.getLastName());
    }

    @Test
    void getStudentById_notFound_shouldThrowException() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> studentService.getStudentById(99L));

        assertEquals("Student not found", ex.getMessage());
    }

    @Test
    void updateStudent_shouldUpdateFields() {
        Student updated = new Student();
        updated.setFirstName("Murad");
        updated.setLastName("Quliyev");
        updated.setEmail("murad@gmail.com");
        updated.setEnrollmentYear(2023);
        updated.setTuitionFee(4000.0);
        updated.setPaidAmount(4000.0);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any())).thenReturn(student);

        Student result = studentService.updateStudent(1L, updated);

        assertEquals("Murad", result.getFirstName());
        assertEquals("Quliyev", result.getLastName());
    }

    @Test
    void deleteStudent_shouldDeleteEnrollmentsAndStudent() {
        when(enrollmentRepository.findByStudentId(1L)).thenReturn(List.of());

        studentService.deleteStudent(1L);

        verify(enrollmentRepository, times(1)).findByStudentId(1L);
        verify(studentRepository, times(1)).deleteById(1L);
    }
}
