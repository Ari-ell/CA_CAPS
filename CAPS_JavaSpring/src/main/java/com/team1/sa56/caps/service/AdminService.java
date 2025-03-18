package com.team1.sa56.caps.service;

import java.util.List;

import com.team1.sa56.caps.model.Admin;
import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.Lecturer;
import com.team1.sa56.caps.model.Student;

public interface AdminService {
    
    // manage admin details
    void createAdmin(Admin admin);
    void saveAdmin(Admin admin);
    void deleteAdmin(Admin admin);
    void changeAdmin(Admin admin);
    
    // manage course details
    void saveCourse(Course course);
    void deleteCourse(Course course);
    void changeCourse(Course course);
    List<Course> listAllCourses();

    List<Course> getAllCourses();
    Course getCourseById(Long id);
    Course updateCourse(Course course);
    void softDeleteCourse(Long id);
    
    // manage lecturer details
    void createLecturer(Lecturer lecturer);
    void saveLecturer(Lecturer lecturer);
    void deleteLecturer(Lecturer lecturer);
    void changeLecturer(Lecturer lecturer);
    List<Lecturer> listAllLecturers();
    Lecturer getLecturerById(Long id);
    
    // manage student details
    void createStudent(Student student);
    void saveStudent(Student student);
    void deleteStudent(Student student);
    void changeStudent(Student student);
    List<Student> listAllStudents();
    
    // manage enrollment details
    void saveEnrollment(Enrollment enrollment);
    void removeEnrollment(Enrollment enrollment);
    List<Enrollment> listAllEnrollments();
}
