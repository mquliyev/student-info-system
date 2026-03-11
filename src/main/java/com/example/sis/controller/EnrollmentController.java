package com.example.sis.controller;

import com.example.sis.entity.Enrollment;
import com.example.sis.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/{studentId}/{courseId}")
    public Enrollment enroll(@PathVariable Long studentId, @PathVariable Long courseId) {
        return enrollmentService.enrollStudentToCourse(studentId, courseId);
    }

    @PutMapping("/{enrollmentId}/grade")
    public Enrollment assignGrade(@PathVariable Long enrollmentId, @RequestParam Double score) {
        return enrollmentService.assignGrade(enrollmentId, score);
    }
}
