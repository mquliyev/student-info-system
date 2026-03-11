package com.example.sis;

import com.example.sis.entity.Course;
import com.example.sis.repository.CourseRepository;
import com.example.sis.repository.EnrollmentRepository;
import com.example.sis.service.CourseService;
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
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private CourseService courseService;

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setId(1L);
        course.setCode("CS101");
        course.setName("Introduction to Programming");
        course.setCredits(3);
        course.setCapacity(30);
    }

    @Test
    void addCourse_shouldReturnSavedCourse() {
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.addCourse(course);

        assertEquals("CS101", result.getCode());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void getAllCourses_shouldReturnList() {
        when(courseRepository.findAll()).thenReturn(List.of(course));

        List<Course> result = courseService.getAllCourses();

        assertEquals(1, result.size());
        assertEquals("CS101", result.get(0).getCode());
    }

    @Test
    void getCourseById_shouldReturnCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course result = courseService.getCourseById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Introduction to Programming", result.getName());
    }

    @Test
    void getCourseById_notFound_shouldThrowException() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.getCourseById(99L));

        assertEquals("Course not found", ex.getMessage());
    }

    @Test
    void updateCourse_shouldUpdateFields() {
        Course updated = new Course();
        updated.setCode("CS102");
        updated.setName("Data Structures");
        updated.setCredits(4);
        updated.setCapacity(25);

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(any())).thenReturn(course);

        Course result = courseService.updateCourse(1L, updated);

        assertEquals("CS102", result.getCode());
        assertEquals("Data Structures", result.getName());
    }

    @Test
    void deleteCourse_shouldDeleteEnrollmentsAndCourse() {
        when(enrollmentRepository.findByCourseId(1L)).thenReturn(List.of());

        courseService.deleteCourse(1L);

        verify(enrollmentRepository, times(1)).findByCourseId(1L);
        verify(courseRepository, times(1)).deleteById(1L);
    }
}