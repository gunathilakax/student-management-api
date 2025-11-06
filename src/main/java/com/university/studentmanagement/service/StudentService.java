package com.university.studentmanagement.service;

import com.university.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);
    List<Student> getAllStudents();
    Student getStudentById(Long id);
    Student updateStudent(Long id, Student studentDetails);
    void deleteStudent(Long id);
    Page<Student> getAllStudents(Pageable pageable);
    List<Student> searchByName(String name);
    List<Student> searchByCourse(String course);
    List<Student> searchByNameOrCourse(String keyword);
}