package com.team1.sa56.caps.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team1.sa56.caps.Util.Grade;
import com.team1.sa56.caps.exception.CourseNotFound;
import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.Student;
import com.team1.sa56.caps.model.User;
import com.team1.sa56.caps.service.CourseService;
import com.team1.sa56.caps.service.EnrollmentService;
import com.team1.sa56.caps.service.StudentService;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    
    @Autowired
    StudentService studService;

    @Autowired
    EnrollmentService enrolService;

    @Autowired
    CourseService courseService;

    @GetMapping(value = "/home")
    public String viewHome(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        Student student = studService.findStudentByEmail(user.getEmail());
        
        model.addAttribute("user", user);
        model.addAttribute("student", student);
        model.addAttribute("enrollments", (List<Enrollment>) enrolService.getApprovedEnrolByStudentId(student.getId()));
        
        return "student/student-home";
    }

    @GetMapping(value = "/grades")
    public String viewGrades(HttpSession session, Model model){
        User user = (User) session.getAttribute("user");
        Student student = studService.findStudentByEmail(user.getEmail());
        List<Enrollment> enrollList = enrolService.getApprovedEnrolByStudentId(student.getId());
        List<Grade> studGradeList = enrolService.getGradesFromEnrollment(enrollList);
        Double gpa = studService.getGpaFromGrades(studGradeList);

        model.addAttribute("user", user);
        model.addAttribute("student", student);
        model.addAttribute("enrollments", enrollList);
        model.addAttribute("grades", studGradeList);
        model.addAttribute("gpa", gpa);
        
        return "student/student-view-grades";
    }

        @GetMapping("/courses")
        public String showAvailCourse(HttpSession session, Model model) {
        
        User user = (User) session.getAttribute("user");
        
        Student student = studService.findStudentByEmail(user.getEmail());
        
        List<Course> notEnrolCourseList = courseService
                        .findCourseNotEnrolByStudent(courseService
                        .findCourseByStudentId(student.getId()));

        List<Integer> courseEnrolCount = courseService.getCourseEnrolCount(notEnrolCourseList);
        List<Integer> courseCapacity = notEnrolCourseList
                                            .stream()
                                            .mapToInt(c -> c
                                            .getEnrollments().size() < c.getCapacity()  ? 1 : 0)
                                            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        model.addAttribute("user", user);
        model.addAttribute("student", student);
        model.addAttribute("courses", notEnrolCourseList);
        model.addAttribute("enrolCount", courseEnrolCount);
        model.addAttribute("courseCapacity", courseCapacity);
        
        return "student/student-enrol-course";
    }

    @GetMapping(value = "/courses/{courseId}")
    public String enrolStudentToCourse(@PathVariable("courseId") Long courseId,  
                        HttpSession session) throws CourseNotFound {
        User user = (User) session.getAttribute("user");
        Student student = studService.findStudentByEmail(user.getEmail());
        Course course = courseService.findCourseById(courseId);
        
        enrolService.enrolStudentInCourse(student, course);
        
        return "forward:/student/courses";
    }
}
