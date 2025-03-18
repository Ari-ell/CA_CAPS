package com.team1.sa56.caps.service;

import java.util.List;

import com.team1.sa56.caps.Util.Grade;
import com.team1.sa56.caps.model.Student;

public interface StudentService {

    List<Student> findAll();
    
    Student findById(Long id);

    Student findByUsername(String username);

    Double getGpaFromGrades(List<Grade> gradeList);

    Student findStudentByEmail(String email);

    List<Student> findAllStudents();
}
