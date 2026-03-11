package com.example.sis.controller;

import com.example.sis.entity.Enrollment;
import com.example.sis.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @PostMapping("/{studentId}/{courseId}")
    public ResponseEntity<Enrollment> enroll(@PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.enrollStudentToCourse(studentId, courseId));
    }

    @PutMapping("/{enrollmentId}/grade")
    public ResponseEntity<Enrollment> assignGrade(@PathVariable Long enrollmentId, @RequestParam Double score) {
        return ResponseEntity.ok(enrollmentService.assignGrade(enrollmentId, score));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.noContent().build();
    }
}
