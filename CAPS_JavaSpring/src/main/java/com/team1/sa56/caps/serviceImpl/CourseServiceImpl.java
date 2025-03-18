package com.team1.sa56.caps.serviceImpl;

import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;


import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.repository.CourseRepository;
import com.team1.sa56.caps.repository.EnrollmentRepository;
import com.team1.sa56.caps.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService{

    @Resource
    private CourseRepository courseRepo;

    @Resource
    private EnrollmentRepository enrolRepo;

    @Override
    public Course findCourseByCourseCode(String courseCode) {
        return courseRepo.findByCourseCode(courseCode);
    }

    @Override
    public Course findCourseById(Long courseId) {
        return courseRepo.findById(courseId).get();
    }

    @Override
    public List<Course> getAllCoursesWithLecturers() { 
        return courseRepo.findAllWithLecturers();
    }    
    
    @Override
    public List<Course> findCourseByStudentId(Long stuId){
        return enrolRepo.findCourseByStudentId(stuId);
    }

    @Override
    public List<Course> findCourseNotEnrolByStudent(List<Course> enrolCourseList) {
        return courseRepo.findCourseNotEnrolByStudent(enrolCourseList);
    }

    @Override
    public List<Integer> getCourseEnrolCount(List<Course> courseList) {
        return courseList.stream()
                        .mapToInt(x -> x.getEnrollments().size())
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public List<Integer> getCourseDuration(List<Course> courseList){
        return courseList.stream()
                            .mapToInt(course -> 
                            (int)ChronoUnit.DAYS.between(course.getStartDate(), course.getEndDate()) +1)
                            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    @Override
    public Page<Course> findByDeletedFalse(PageRequest of) {
        return courseRepo.findByDeletedFalse(of);
    }

    @Override
    public List<Course> getCourseNotDeleted(List<Course> allCourses){
        return allCourses.stream()
                .filter(c -> c.isDeleted() == false)
                .toList();
    }

    @Override
    public List<Course> findCourseNotTaughtByLect(List<Course> courseList){
        return courseRepo.findCourseNotEnrolByStudent(courseList);
    }

}
