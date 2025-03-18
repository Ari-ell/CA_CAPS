package com.team1.sa56.caps.serviceImpl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.Lecturer;
import com.team1.sa56.caps.repository.AdminRepository;
import com.team1.sa56.caps.repository.CourseRepository;
import com.team1.sa56.caps.repository.EnrollmentRepository;
import com.team1.sa56.caps.repository.LecturerRepository;
import com.team1.sa56.caps.repository.StudentRepository;
import com.team1.sa56.caps.service.LecturerService;

@Service
public class LecturerServiceImpl implements LecturerService {
    @Resource
    private AdminRepository adminRepo;
    
    @Resource
    private LecturerRepository lectRepo;
    
    @Resource
    private StudentRepository studRepo;
    
    @Resource
    private CourseRepository courseRepo;
    
    @Resource
    private EnrollmentRepository enrollRepo;

    @Override
    public Lecturer findById(Long id) {
        return lectRepo.findById(id).get();
    }

    @Override
    public Lecturer findLecturerByUsername(String username) {
        return lectRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public void removeLecturerFromCourse(Lecturer lecturer, Course course) {
        lecturer.removeTeaching(course);
        lectRepo.saveAndFlush(lecturer);
    }

    @Override
    public List<Enrollment> findEnrollmentByCourseId(Long courseId) {
        return (List<Enrollment>) courseRepo.findById(courseId).get().getEnrollments();
    }

    @Override
    @Transactional
    public void removeCourse(Course c) {
        courseRepo.delete(c);
    }

    @Override
    public Lecturer findLecturerByEmail(String email) {
        return lectRepo.findByEmail(email);
    }

    @Override
    public List<Course> findAllCourseTeaching(Long lecturerId){
        Lecturer lecturer = lectRepo.findById(lecturerId).orElseThrow(null);
        List<Course> courseList =  (List<Course>) lecturer.getTeaching();
        return courseList;
    }

    @Override
    public List<Lecturer> findAll() {
        return lectRepo.findAll();
    }

    @Override
    @Transactional
    public void addLecturerToCourse(Lecturer lecturer, Course course) {
        lecturer.addTeaching(course);
        lectRepo.saveAndFlush(lecturer);
    }
}
