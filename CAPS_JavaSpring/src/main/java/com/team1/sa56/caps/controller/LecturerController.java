package com.team1.sa56.caps.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team1.sa56.caps.Util.Grade;
import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.EnrollmentStatusEnum;
import com.team1.sa56.caps.model.Lecturer;
import com.team1.sa56.caps.model.Student;
import com.team1.sa56.caps.model.User;
import com.team1.sa56.caps.service.CourseService;
import com.team1.sa56.caps.service.EnrollmentService;
import com.team1.sa56.caps.service.LecturerService;
import com.team1.sa56.caps.service.StudentService;

@Controller
@RequestMapping(value="/lecturer")
public class LecturerController {

    @Autowired
    LecturerService lectService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studService;

    @Autowired
    EnrollmentService enrolService;

    @GetMapping(value = "/home")
    public String viewLecturerHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        Lecturer lecturer = lectService.findLecturerByEmail(user.getEmail());
        List<Course> teachings = (List<Course>) lecturer.getTeaching();
        List<Course> curTeachings = courseService.getCourseNotDeleted(teachings);
        List<Integer> coursEnrolCount = curTeachings
                                        .stream()
                                        .mapToInt(x -> 
                                        x.getEnrollments().size())
                                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        List<Integer> courseDuration = courseService.getCourseDuration(curTeachings);
        
        model.addAttribute("user", user);
        model.addAttribute("lecturer", lecturer);
        model.addAttribute("courses", curTeachings);
        model.addAttribute("enrolCount", coursEnrolCount);
        model.addAttribute("courseDuration", courseDuration);

        return "lecturer/lecturer-home";
    }
    
    // view enrollment details and filter by courses
    @GetMapping(value = "/enrollments/{courseId}")  
    public String getCourseEnrollments(HttpSession session, @PathVariable("courseId") Long courseId, Model model) {
        
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Lecturer lecturer = lectService.findLecturerByEmail(user.getEmail());
        
        if (courseId == 0) { // get all
            List<Course> allCourses = lectService.findAllCourseTeaching(lecturer.getId());
            List<Course> notDeletedCourse = courseService.getCourseNotDeleted(allCourses);
            List<Enrollment> allEnrollments = enrolService.findEnrolByStatusNotRejected(notDeletedCourse);
            List<Grade> grades = enrolService.getGradesFromEnrollment(allEnrollments);
            
            model.addAttribute("courses", allCourses);
            model.addAttribute("enrollments", allEnrollments);
            model.addAttribute("grades", grades);
        } else { // get by courseId
            Course chosenCourse = courseService.findCourseById(courseId);
            List<Enrollment> courseEnrollments = enrolService.getApprovedEnrolByCourseId(courseId);
            List<Grade> grades = enrolService.getGradesFromEnrollment(courseEnrollments);
            
            model.addAttribute("courses", chosenCourse);
            model.addAttribute("enrollments", courseEnrollments);
            model.addAttribute("grades", grades);
        }
        return "lecturer/lecturer-view-enrollments";
    }

    // view student enrollment details for a particular course
    @GetMapping(value = "/enrollments/{studentId}/{courseId}")
    public String viewStudentGrades(HttpSession session, @PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId, Model model) {
        
        // get lecturer details
        User user = (User) session.getAttribute("user");
        Student student = studService.findById(studentId);
        Enrollment enrol = enrolService.findByStudentCourse(student.getId(), courseId);
        
        model.addAttribute("user", user);
        model.addAttribute("student", student);
        model.addAttribute("enrollment", enrol);
        
        return "lecturer/lecturer-view-grades";
    }

    // update the student marks
    @GetMapping(value = "/save/{studentId}/{courseId}")
    public String saveStudentGrades(@RequestParam("score") int score, @PathVariable("studentId") Long studentId,
                                        @PathVariable("courseId") Long courseId) {

        enrolService.updateStudentScore(studentId, courseId, score);

        return "redirect:/lecturer/enrollments/" + studentId + "/" + courseId;
    }

        // page for submit rejection request
    @RequestMapping(value = "/course/rejection")
    public String getLectCourses(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Lecturer lecturer = lectService.findLecturerByEmail(user.getEmail());

        List<Course> lectCourses = (List<Course>) lecturer.getTeaching();

        model.addAttribute("lectCourses", lectCourses);
        model.addAttribute("user", user);
        return "lecturer/lecturer-edit-request";
    }

    // page after choose course for rejection
    @PostMapping(value = "course/rejection/student")
    public String pickStudentToReject(Model model, @RequestParam(value = "courseid") Long id, HttpSession session) {

        User user = (User) session.getAttribute("user");

        Course chosenCourse = courseService.findCourseById(id);
        List<Student> approveStudent = enrolService.findApproveStudentsByCourse(id);

        model.addAttribute("chosenCourse", chosenCourse);
        model.addAttribute("approveStudent", approveStudent);
        model.addAttribute("user", user);
        return "lecturer/lecturer-edit-request-student";
    }

    // save the rejection request - change enrollstatus to pending
    @PostMapping(value = "course/rejection/save/{courseid}")
    public String saveRejectionRequest(@RequestParam(value = "studentid") Long id,
            @PathVariable("courseid") Long course) {
            
        Enrollment enrollment = enrolService.findByStudentCourse(id, course);

        enrollment.setEnrollmentStatus(EnrollmentStatusEnum.PENDING);
        enrolService.changEnrollment(enrollment);

        return "redirect:/lecturer/course/rejection/success";
    }

    // 2 method for showing success page 
    @RequestMapping("/course/rejection/success")
    public String updateSuccess(RedirectAttributes redirectAttributes, HttpSession session) {
        redirectAttributes.addFlashAttribute("message", "Operation was successful!");
        session.setAttribute("redirecturl", "/lecturer/course/rejection");
        return "redirect:/lecturer/course/rejection/success/page";
    }

     @RequestMapping("/course/rejection/success/page")
     public String showSuccessPage(HttpServletRequest request, HttpSession session, Model model) {

         String redirectUrl = (String) session.getAttribute("redirecturl");
         session.removeAttribute("redirecturl");
         model.addAttribute("redirectUrl", redirectUrl);

         return "lecturer/lecturer-request-success";
     }
}
