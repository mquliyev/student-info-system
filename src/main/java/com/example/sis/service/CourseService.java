package com.example.sis.service;

import com.example.sis.entity.Course;
import com.example.sis.entity.Enrollment;
import com.example.sis.repository.CourseRepository;
import com.example.sis.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course updateCourse(Long id, Course updated) {
        Course course = getCourseById(id);
        course.setCode(updated.getCode());
        course.setName(updated.getName());
        course.setCredits(updated.getCredits());
        course.setCapacity(updated.getCapacity());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(id);
        enrollmentRepository.deleteAll(enrollments);
        courseRepository.deleteById(id);
    }
}