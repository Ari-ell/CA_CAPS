package com.team1.sa56.caps.serviceImpl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.team1.sa56.caps.model.Admin;
import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.Lecturer;
import com.team1.sa56.caps.model.Student;
import com.team1.sa56.caps.repository.AdminRepository;
import com.team1.sa56.caps.repository.CourseRepository;
import com.team1.sa56.caps.repository.EnrollmentRepository;
import com.team1.sa56.caps.repository.LecturerRepository;
import com.team1.sa56.caps.repository.StudentRepository;
import com.team1.sa56.caps.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

    @Resource
    private AdminRepository adminRepo;
    
    @Resource
    private LecturerRepository lectRepo;
    
    @Resource
    private StudentRepository studRepo;
    
    @Resource
    private CourseRepository courseRepository;
    
    @Resource
    private EnrollmentRepository enrollRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    // Admin Management Methods

    @Override
    @Transactional
    public void saveAdmin(Admin admin) {
        adminRepo.save(admin);
    }
    
    @Override
    @Transactional
    public void deleteAdmin(Admin admin) {
        adminRepo.delete(admin);
    }

    @Override
    @Transactional
    public void changeAdmin(Admin admin) {
        adminRepo.saveAndFlush(admin);
    }

    // Course Management Methods
    
    @Override
    @Transactional
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }
    
    @Override
    @Transactional
    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }
    
    @Override
    @Transactional
    public void changeCourse(Course course) {
        courseRepository.saveAndFlush(course);
    }
    
    // Lecturer Management Methods

    @Override
    @Transactional
    public void saveLecturer(Lecturer lecturer) {
        lectRepo.save(lecturer);
    }
    
    @Override
    @Transactional
    public void deleteLecturer(Lecturer lecturer) {
        lectRepo.delete(lecturer);
    }
    
    @Override
    @Transactional
    public void changeLecturer(Lecturer lecturer) {
        lectRepo.saveAndFlush(lecturer);
    }
    
    // Student Management Methods

    @Override
    @Transactional
    public void saveStudent(Student student) {
        studRepo.save(student);
    }
    
    @Override
    @Transactional
    public void deleteStudent(Student student) {
        studRepo.delete(student);
    }
    
    @Override
    @Transactional
    public void changeStudent(Student student) {
        studRepo.saveAndFlush(student);
    }
    
    @Override
    public List<Course> listAllCourses() {
        return courseRepository.findAll();
    }
    
    @Override
    public List<Lecturer> listAllLecturers() {
        return lectRepo.findAll();
    }
    
    @Override
    public List<Student> listAllStudents() {
        return studRepo.findAll();
    }
    
    @Override
    public List<Enrollment> listAllEnrollments() {
        return enrollRepo.findAll();
    }
    
    @Override
    @Transactional
    public void saveEnrollment(Enrollment enrollment) {
        enrollRepo.save(enrollment);
    }
    
    @Override
    @Transactional
    public void removeEnrollment(Enrollment enrollment) {
        enrollRepo.delete(enrollment);
    }

    //courses  
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void softDeleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Course not found"));
        
        // Soft delete courseEnrollment
        List<Enrollment> enrollment = enrollRepo.findEnrolByCourseId(id);
        enrollment.forEach(e -> {
            e.setDeleted(true);
            enrollRepo.save(e);
        });
        
        // Soft delete lecturer_course relationship
        for (Lecturer lecturer: course.getLecturers()){
            lecturer.setDeleted(true);
            lectRepo.save(lecturer);
        }
        
        // Soft delete the course itself
        course.setDeleted(true);
        courseRepository.save(course);
    }

    @Override
    @Transactional
    public Lecturer getLecturerById(Long id) {
        return lectRepo.findById(id).orElseThrow(null);
    }

    @Override
    @Transactional
    public void createAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepo.save(admin);
    }

    @Override
    @Transactional
    public void createLecturer(Lecturer lecturer) {
        lecturer.setPassword(passwordEncoder.encode(lecturer.getPassword()));
        lectRepo.save(lecturer);
    }

    @Override
    @Transactional
    public void createStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studRepo.save(student);
    }
}
