package com.team1.sa56.caps.serviceImpl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.team1.sa56.caps.Util.Grade;
import com.team1.sa56.caps.Util.GradeMapping;
import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.EnrollmentStatusEnum;
import com.team1.sa56.caps.model.Student;
import com.team1.sa56.caps.repository.EnrollmentRepository;
import com.team1.sa56.caps.repository.StudentRepository;
import com.team1.sa56.caps.service.EnrollmentService;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Resource
    EnrollmentRepository enrolRepo;

    @Resource
    StudentRepository studRepo;

    @Override
    public Enrollment findByStudentCourse(Long studentId, Long courseId) {
        return enrolRepo.findEnrolByStudentCourse(studentId, courseId);
        
    }

    @Override
    @Transactional
    public void updateStudentScore(Long studentId, Long courseId, int score) {
        Enrollment enrolToSave = enrolRepo.findEnrolByStudentCourse(studentId, courseId);
        enrolToSave.setScore(score);
        enrolRepo.save(enrolToSave);
    }

    @Override
    public List<Enrollment> getApprovedEnrolByCourseId(Long courseId) {
        return enrolRepo.getApprovedEnrolByCourseId(courseId);
    }
    
    @Override
    public List<Grade> getGradesFromEnrollment(List<Enrollment> enrollments){
        return enrollments
                .stream()
                .map(enrol -> enrol.getScore() != null ?
                 GradeMapping.calcGrade(enrol.getScore()) : null)
                .toList();
    }
    
    @Override
    public List<Enrollment> getApprovedEnrolFromCourseList(List<Course> courseList) {
        return courseList.stream().flatMap(course -> 
        enrolRepo.getApprovedEnrolByCourseId(course.getId())
        .stream())
        .toList();
    }

    @Override
    public List<Enrollment> findEnrollmentsByStudentId(Long studentId) {
        Student student = studRepo.findById(studentId).get();
        return (List<Enrollment>) student.getEnrollments();
    }

    @Override
    public List<Enrollment> getApprovedEnrolByStudentId(Long stuId) {
        List<Enrollment> enrolList = enrolRepo.findEnrolByStudentId(stuId);
        return enrolList.stream()
                .filter(e -> e.getEnrollmentStatus() == EnrollmentStatusEnum.APPROVED)
                .toList();
    }

    @Override
    @Transactional
    public void enrolStudentInCourse(Student stu, Course course) {
        enrolRepo.save(new Enrollment(stu, course));
    }

    @Override
    public List<Enrollment> findPendingRequest() {
        return enrolRepo.findPendingRequest();
    }

    @Override
    @Transactional
    public Enrollment changEnrollment(Enrollment enrollment) {
        return enrolRepo.save(enrollment);
    }

    @Override
    public List<Student> findApproveStudentsByCourse(Long id){
        return enrolRepo.findApproveStudentsByCourse(id);
    }

    @Override
    public List<Enrollment> findAll() {
        return enrolRepo.findAll();
    }

    @Override
    public List<Enrollment> findEnrolByStatusNotRejected(List<Course> courses) {
        return courses.stream().flatMap(course -> 
        enrolRepo.findEnrolByCourseNotRejected(course.getId())
        .stream())
        .toList();
    }    
    
    @Override
    public List<Enrollment> findRejectedStudent(){
        return enrolRepo.findRejectedStudent();
    }
}
