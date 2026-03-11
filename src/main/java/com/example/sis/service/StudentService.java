package com.example.sis.service;

import com.example.sis.entity.Student;
import com.example.sis.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(Long id, Student updated) {
        Student student = getStudentById(id);
        student.setFirstName(updated.getFirstName());
        student.setLastName(updated.getLastName());
        student.setEmail(updated.getEmail());
        student.setEnrollmentYear(updated.getEnrollmentYear());
        student.setTuitionFee(updated.getTuitionFee());
        student.setPaidAmount(updated.getPaidAmount());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
