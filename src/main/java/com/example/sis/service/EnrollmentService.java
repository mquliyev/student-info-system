package com.example.sis.service;

import com.example.sis.entity.Course;
import com.example.sis.entity.Enrollment;
import com.example.sis.entity.Student;
import com.example.sis.repository.CourseRepository;
import com.example.sis.repository.EnrollmentRepository;
import com.example.sis.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Enrollment enrollStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        int currentCount = enrollmentRepository.countByCourseId(courseId);
        if (currentCount >= course.getCapacity()) {
            throw new RuntimeException("Course is full");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment assignGrade(Long enrollmentId, Double score) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setScore(score);

        if (score >= 91) {
            enrollment.setLetterGrade("A");
            enrollment.setIsFailed(false);
        } else if (score >= 81) {
            enrollment.setLetterGrade("B");
            enrollment.setIsFailed(false);
        } else if (score >= 71) {
            enrollment.setLetterGrade("C");
            enrollment.setIsFailed(false);
        } else if (score >= 51) {
            enrollment.setLetterGrade("D");
            enrollment.setIsFailed(false);
        } else {
            enrollment.setLetterGrade("F");
            enrollment.setIsFailed(true);
        }

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        updateStudentGPA(savedEnrollment.getStudent());
        return savedEnrollment;
    }

    private void updateStudentGPA(Student student) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());

        if (enrollments == null || enrollments.isEmpty()) {
            student.setGpa(0.0);
        } else {
            double totalScore = 0;
            int count = 0;
            for (Enrollment e : enrollments) {
                if (e.getScore() != null) {
                    totalScore += e.getScore();
                    count++;
                }
            }
            student.setGpa(count > 0 ? totalScore / count : 0.0);
        }
        studentRepository.save(student);
    }
}