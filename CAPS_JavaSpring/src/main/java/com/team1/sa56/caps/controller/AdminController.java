package com.team1.sa56.caps.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team1.sa56.caps.validator.CourseValidator;
import com.team1.sa56.caps.exception.CourseNotFound;
import com.team1.sa56.caps.exception.LecturerNotFound;
import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.CourseDTO;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.EnrollmentStatusEnum;
import com.team1.sa56.caps.model.Lecturer;
import com.team1.sa56.caps.model.Student;
import com.team1.sa56.caps.model.User;
import com.team1.sa56.caps.service.AdminService;
import com.team1.sa56.caps.service.CourseService;
import javax.servlet.http.HttpServletRequest;

import com.team1.sa56.caps.service.EnrollmentService;
import com.team1.sa56.caps.service.LecturerService;
import com.team1.sa56.caps.service.StudentService;
import com.team1.sa56.caps.validator.LecturerValidator;
import com.team1.sa56.caps.validator.StudentValidator;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private EnrollmentService enrolService;
    
    @Autowired
    private CourseService courseService;

    @Autowired
    private LecturerService lectService;
    
    @Autowired
    private StudentValidator studentValidator;
        
    @Autowired
    private CourseValidator courseValidator;

    @Autowired
    private LecturerValidator lecturerValidator;
    
    @Autowired
    PasswordEncoder pwEncoder;

    @InitBinder("student")
    private void initStudentBinder(WebDataBinder binder) {
        // add validator here
        binder.addValidators(studentValidator);
    }

    @InitBinder("lecturer")
    private void initLecturerBinder(WebDataBinder binder){
        binder.addValidators(lecturerValidator);
    }

    @InitBinder("course")
    private void initCourseBinder(WebDataBinder binder){
        // add validator here
        binder.addValidators(courseValidator);
    }

    /////////////////
    // CRUD LECTURER
    /////////////////
    @GetMapping("/lecturer/list")
    public String lecturerList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Lecturer> lecturerList = adminService.listAllLecturers();
        
        model.addAttribute("user", user);
        model.addAttribute("lecturerList", lecturerList);
        model.addAttribute("lecturer", new Lecturer());
        return "/admin/admin-lecturer-list";
    }

    @GetMapping("/lecturer/new")
    public String newLecturer(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("lecturer", new Lecturer());
        model.addAttribute("user", user);
        return "/admin/admin-lecturer-new";
    }
  
    @PostMapping("/lecturer/new")
    public String newLecturer(@Valid @ModelAttribute("lecturer") Lecturer lecturer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/admin/admin-lecturer-new";
        }
        lecturer.generateUsername();
        lecturer.setRole("Lecturer");
        adminService.createLecturer(lecturer);
        return "redirect:/admin/lecturer/list";
    }

    @GetMapping("/lecturer/edit/{id}")
    public String editLecturer(HttpSession session, @PathVariable Long id, Model model) {
        User user = (User) session.getAttribute("user");
        Lecturer lecturer = adminService.getLecturerById(id);
        model.addAttribute("user", user);
        model.addAttribute("lecturer", lecturer);
        return "admin/admin-lecturer-edit";
    }

    @PostMapping("/lecturer/edit/{id}")
    public String editLecturer(@PathVariable("id") Long id, @Valid @ModelAttribute("lecturer") Lecturer lecturer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "admin/admin-lecturer-edit";
        }
        adminService.changeLecturer(lecturer);
        return "redirect:/admin/lecturer/list";
    }

    @GetMapping("/lecturer/delete/{id}")
    public String deleteLecturer(@PathVariable Long id) {
        Lecturer lecturer = adminService.getLecturerById(id);
        adminService.deleteLecturer(lecturer);
        return "redirect:/admin/lecturer/list";
    }

    @GetMapping("/lecturer/course/{id}")
    public String viewAllLecturerTeachings(@PathVariable("id") Long lecturerId, 
                    Model model, HttpSession session) throws LecturerNotFound{
        User user = (User) session.getAttribute("user");
        Lecturer lecturer = lectService.findById(lecturerId);
        
        // get courses taught
        if(lecturer.getTeaching().size() != 0){
            List<Course> coursesTaught  = courseService.getCourseNotDeleted((List<Course>) lecturer.getTeaching());
            List<Course> coursesNotTaught = courseService.findCourseNotTaughtByLect(coursesTaught);
            List<Course> coursesNotTaughtNotDeleted = courseService.getCourseNotDeleted(coursesNotTaught);
            model.addAttribute("coursesTaught", coursesTaught);
            model.addAttribute("coursesNotTaught", coursesNotTaughtNotDeleted);
        } else {
            List<Course> coursesNotTaught = courseService.findAll();
            List<Course> coursesNotTaughtNotDeleted = courseService.getCourseNotDeleted(coursesNotTaught);
            model.addAttribute("coursesNotTaught", coursesNotTaughtNotDeleted);
        }
        model.addAttribute("user", user);
        model.addAttribute("lecturer", lecturer);
        return "admin/admin-manage-lecturer-course";
    }

    @GetMapping("/lecturer/course-add/{lecturerId}/{courseId}")
    public String addLecturerToCourse(@PathVariable("lecturerId") Long lecturerId, 
                    @PathVariable("courseId") Long courseId) throws CourseNotFound{
            Lecturer lecturer = lectService.findById(lecturerId);
            Course course = courseService.findCourseById(courseId);
            lectService.addLecturerToCourse(lecturer, course);
        return "redirect:/admin/lecturer/course/{lecturerId}";
    }

    @GetMapping("/lecturer/course-remove/{lecturerId}/{courseId}")
    public String removeLecturerFromCourse(@PathVariable("lecturerId") Long lecturerId, 
                    @PathVariable("courseId") Long courseId) throws CourseNotFound {
        Lecturer lecturer = lectService.findById(lecturerId);
        Course course = courseService.findCourseById(courseId);
        lectService.removeLecturerFromCourse(lecturer, course);

        return "redirect:/admin/lecturer/course/{lecturerId}";
    }

    /////////////////
    // CRUD COURSE
    /////////////////
    @GetMapping("/courses")
    public String coursesList(HttpSession session, @RequestParam(defaultValue = "0") int page, Model model) {
        User user = (User) session.getAttribute("user");
        
        int pageSize = 4; // Update the page size here

        Page<Course> coursePage = courseService.findByDeletedFalse(PageRequest.of(page, pageSize));
        List<Course> courses = coursePage.getContent();

        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (Course course : courses) {
            int numberOfStudentsEnrolled = course.getEnrollments().size();
            int numberOfLecturersTeaching = course.getLecturers().size();
            List<String> lecturerNames = course.getLecturers().stream()
                .map(Lecturer::getFirstName)
                .collect(Collectors.toList());

            CourseDTO courseDTO = new CourseDTO(course, numberOfStudentsEnrolled, numberOfLecturersTeaching, lecturerNames);
            courseDTOs.add(courseDTO);
        }
        model.addAttribute("user", user);
        model.addAttribute("courses", courseDTOs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", coursePage.getTotalPages());

        return "admin/admin-view-courses";    
    }

    @GetMapping("/courses/{id}")
    public String courseDetail(HttpSession session, @PathVariable Long id, Model model) {
        //Course course = courseRepository.findById(id).orElse(null);
        User user = (User) session.getAttribute("user");
        Course course = adminService.getCourseById(id);

        if (course != null) {
            List<String> lecturerNames = course.getLecturers().stream()
                .map(Lecturer::getFirstName)
                .collect(Collectors.toList());

            model.addAttribute("user", user);
            model.addAttribute("course", course);
            model.addAttribute("lecturerNames", lecturerNames);
            return "admin/admin-view-course-detail";
        } 
        // Handle course not found scenario
        return "error_page";
    }

    @GetMapping("/courses/new")
    public String createCourseForm(HttpSession session, Model model) {
        Course course = new Course();
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("course", course);
        return "admin/admin-create-course";
    }

    @PostMapping("/courses/new")
    public String saveCourse(HttpSession session, Model model, @Valid @ModelAttribute("course") Course course, 
            BindingResult bindingResult){
        if(bindingResult.hasErrors()) {  
            // Course course2 = new Course();
            // model.addAttribute("course", course2);
            return "admin/admin-create-course";
        }
        adminService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(HttpSession session, @PathVariable Long id, Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("course", adminService.getCourseById(id));
        return "admin/admin-edit-course";
    }

    @PostMapping("courses/edit/{id}")
    public String updateCourse(@PathVariable Long id, Model model, @Valid @ModelAttribute("course") Course course, 
                     BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return "admin/admin-edit-course";
        }

        Course existingCourse = adminService.getCourseById(id);
        existingCourse.setId(id);
        existingCourse.setCourseCode(course.getCourseCode());
        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());
        existingCourse.setCapacity(course.getCapacity());
        existingCourse.setCredits(course.getCredits());
        existingCourse.setStartDate(course.getStartDate());
        existingCourse.setEndDate(course.getEndDate());

        adminService.updateCourse(existingCourse);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/soft-delete/{id}") 
    public String softDeleteCourse(@PathVariable Long id){
        adminService.softDeleteCourse(id);
        return "redirect:/admin/courses";
    }

    /////////////////
    // CRUD ENROLLMENT
    /////////////////
    
    //admin page to see list of approved request = rejected student
    @GetMapping(value = "/request/history")
    public String viewAdminRequestHistory(Model model, HttpSession session){
         User user = (User) session.getAttribute("user");
        List<Enrollment> rejectedStudent = enrolService.findRejectedStudent();

        model.addAttribute("rejectedStudent", rejectedStudent);
        model.addAttribute("user", user);
        return "admin/admin-view-request-history";
    }

    // admin page to see list of request at url admin/request
    @GetMapping(value = "/request")
    public String viewAdminRequest(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        List<Enrollment> pendingRequest = enrolService.findPendingRequest();

        model.addAttribute("pendingRequest", pendingRequest);
        model.addAttribute("user", user);
        return "admin/admin-view-request";
    }

    // action for approve/reject by admin for each student-course
    @GetMapping(value = "/request/{action}/{id}/{course}")
    public String updateRequest(@PathVariable Long id, @PathVariable Long course, @PathVariable String action,
            Model model) {

        Enrollment enrollment = enrolService.findByStudentCourse(id, course);

        if (action.equals("approve")) {
            enrollment.setEnrollmentStatus(EnrollmentStatusEnum.REJECTED);
            enrolService.changEnrollment(enrollment);
        } else if (action.equals("reject")) {
            enrollment.setEnrollmentStatus(EnrollmentStatusEnum.APPROVED);
            enrolService.changEnrollment(enrollment);
        }
        return "redirect:/admin/request/success";
    }

    // 2 method redirect and show successpage for 3s and redirect back
    @RequestMapping("/request/success")
    public String updateSuccess(RedirectAttributes redirectAttributes, HttpSession session) {
        redirectAttributes.addFlashAttribute("message", "Operation was successful!");
        session.setAttribute("redirecturl", "/admin/request");
        return "redirect:/admin/request/successPage";
    }

    @RequestMapping("/request/successPage")
    public String showSuccessPage(HttpServletRequest request, HttpSession session, Model model) {

        String redirectUrl = (String) session.getAttribute("redirecturl");

        session.removeAttribute("redirecturl");

        model.addAttribute("redirectUrl", redirectUrl);

        return "admin/admin-request-success";
    }

    /////////////////
    // CRUD STUDENT
    /////////////////

    // this is the (default) view page
    @GetMapping("/studentManagement/list")
    public String studentListPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        
        // get list of students, add to model
        List<Student> studentList = studentService.findAllStudents();
        
        model.addAttribute("user", user);
        model.addAttribute("studentList", studentList);
        return "admin/admin-student-list";
    }

    // to render the form page to create a new student
    @GetMapping(value = "/studentManagement/create")
    public String newStudentPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("student", new Student());
        return "admin/admin-student-new-2";
    }

    @PostMapping(value = "/studentManagement/create")
    // add @Valid later for validators
    public String createNewStudent(HttpSession session, Model model, @ModelAttribute @Valid Student student, 
                BindingResult result) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        // bounce back to form if there are any errors
        if (result.hasErrors()) {
            return "admin/admin-student-new-2";
        }

        student.setEnrollmentDate(LocalDate.now());
        student.setRole("Student");

        // adds student to repo
        adminService.createStudent(student);

        // fill in correct url later
        return "redirect:/admin/studentManagement/list";
    }

    @GetMapping(value = "/studentManagement/edit/{studentId}")
    public String editStudentPage(HttpSession session, @PathVariable Long studentId, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Student student = studentService.findById(studentId);
        model.addAttribute("student", student);
        return "admin/admin-student-edit-2";
    }

    // to render the form page
    // implement "throws StudentNotFound"
    @PostMapping(value = "/studentManagement/edit/{id}")
    public String editStudent(@PathVariable("id") Long studentId, @Valid @ModelAttribute Student student, 
            BindingResult result) {
        
        if (result.hasErrors()) {
            // model.addAttribute("student", student);
            // do i need to add stuIdList to the model?
            return "admin/admin-student-edit-2";
        }
        adminService.changeStudent(student);

        String message = "Student was successfully updated.";
        System.out.println(message);

        return "redirect:/admin/studentManagement/list";
    }

    @GetMapping(value = "/studentManagement/delete/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {

        Student student = studentService.findById(studentId);
        adminService.deleteStudent(student);

        String message = "Student " + student.getFirstName() + " was successfully deleted.";
        System.out.println(message);

        // redirect vs forwarding?
        return "redirect:/admin/studentManagement/list";
    }
}
