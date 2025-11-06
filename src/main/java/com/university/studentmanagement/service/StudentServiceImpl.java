package com.university.studentmanagement.service;

import com.university.studentmanagement.entity.Student;
import com.university.studentmanagement.exception.DuplicateResourceException;
import com.university.studentmanagement.exception.ResourceNotFoundException;
import com.university.studentmanagement.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exists");
        }
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        if (!existingStudent.getEmail().equals(studentDetails.getEmail())
                && studentRepository.existsByEmail(studentDetails.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentDetails.getEmail() + " already exists");
        }

        existingStudent.setName(studentDetails.getName());
        existingStudent.setEmail(studentDetails.getEmail());
        existingStudent.setCourse(studentDetails.getCourse());
        existingStudent.setAge(studentDetails.getAge());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        studentRepository.delete(student);
    }

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public List<Student> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Student> searchByCourse(String course) {
        return studentRepository.findByCourseIgnoreCase(course);
    }

    @Override
    public List<Student> searchByNameOrCourse(String keyword) {
        return studentRepository.findByNameContainingIgnoreCaseOrCourseIgnoreCase(keyword, keyword);
    }
}