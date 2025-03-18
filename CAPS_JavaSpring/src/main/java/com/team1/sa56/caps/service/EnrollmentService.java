package com.team1.sa56.caps.service;

import java.util.List;

import com.team1.sa56.caps.Util.Grade;
import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.Student;

public interface EnrollmentService{

    List<Enrollment> findAll();
    
    Enrollment findByStudentCourse(Long studentId, Long courseId);

    void updateStudentScore(Long studentId, Long courseId, int score);

    List<Enrollment> getApprovedEnrolByCourseId(Long courseId);

    List<Enrollment> getApprovedEnrolByStudentId(Long stuId);

    List<Grade> getGradesFromEnrollment(List<Enrollment> enrollments);

    List<Enrollment> getApprovedEnrolFromCourseList(List<Course> courseList);

    List<Enrollment> findEnrollmentsByStudentId(Long studentId);

    List<Enrollment> findPendingRequest();

    List<Student> findApproveStudentsByCourse(Long id);

    Enrollment changEnrollment(Enrollment enrollment);

    List<Enrollment> findEnrolByStatusNotRejected(List<Course> courses);

    List<Enrollment> findRejectedStudent();

    void enrolStudentInCourse(Student stu, Course course);
}