package com.team1.sa56.caps.service;

import java.util.List;

import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.Lecturer;

public interface LecturerService {

    List<Lecturer> findAll();

    Lecturer findById(Long id);
    
    Lecturer findLecturerByUsername(String username);

    void removeLecturerFromCourse(Lecturer lecturer, Course course);

    List<Enrollment> findEnrollmentByCourseId(Long courseId);
    
    List<Course> findAllCourseTeaching(Long lecturerId);
    
    void removeCourse(Course course);

    Lecturer findLecturerByEmail(String email);

    public void addLecturerToCourse(Lecturer lecturer, Course course);
} 
