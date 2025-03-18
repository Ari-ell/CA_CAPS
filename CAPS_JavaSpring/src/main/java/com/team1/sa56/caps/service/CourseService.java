package com.team1.sa56.caps.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.team1.sa56.caps.model.Course;

public interface CourseService {
    
    List<Course> findAll();
    
    Course findCourseByCourseCode(String courseCode);
    
    Course findCourseById(Long courseId);

    List<Course> getAllCoursesWithLecturers();
 
    List<Course> findCourseByStudentId(Long stuId);

    List<Course> findCourseNotEnrolByStudent(List<Course> enrolCourseList);

    List<Course> findCourseNotTaughtByLect(List<Course> courseList);

    List<Integer> getCourseEnrolCount(List<Course> courseList);

    List<Integer> getCourseDuration(List<Course> courseList);

    Page<Course> findByDeletedFalse(PageRequest of);

    List<Course> getCourseNotDeleted(List<Course> allCourses);
}
