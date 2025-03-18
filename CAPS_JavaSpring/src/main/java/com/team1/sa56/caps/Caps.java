package com.team1.sa56.caps;

import java.time.LocalDate;

import com.team1.sa56.caps.configuration.CorsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.team1.sa56.caps.model.Admin;
import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.Lecturer;
import com.team1.sa56.caps.model.Student;
import com.team1.sa56.caps.service.AdminService;

@EnableAsync
@SpringBootApplication
@Import(CorsConfig.class)
public class Caps {
	
	@Autowired
	AdminService adminService;
	
	public static void main(String[] args) {
		SpringApplication.run(Caps.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return (args) -> {
			
			// create admin
			Admin admin1 = new Admin("Admin", "ISS", "admin1@iss.com", "password");			
			adminService.createAdmin(admin1);

			// create lecturers
			Lecturer lect1 = new Lecturer("Tin", "ISS", "tin@iss.com", "Tin123456");
			lect1.setDescription("Tin loves teaching, problem solving and self-learning.");
			
			Lecturer lect2 = new Lecturer("Esther", "ISS", "esther@iss.com", "Esther12346");
			lect2.setDescription("Esther specializes in designing and developing internet / mobile solutions to support organizations' needs and business activities.");

			Lecturer lect3 =  new Lecturer("Darryl", "ISS", "darryl@iss.com", "Darryl123456");
			lect3.setDescription("Darryl is a Senior Lecturer and Consultant from the Software Systems Practice at NUS-ISS.");

			Lecturer lect4 =  new Lecturer("CherWah", "ISS", "cherwah@iss.com", "Cherwah123456");
			lect4.setDescription("Cher Wah has over 20 years of software development experience in the Education, Security and Applied R&D domains.");

			adminService.createLecturer(lect1);
			adminService.createLecturer(lect2);
			adminService.createLecturer(lect3);
			adminService.createLecturer(lect4);
			
			// create students
			Student stud1 = new Student("Feroz", "ISS", "feroz@iss.com", "Feroz");
			Student stud2 = new Student("Cheongyi", "ISS", "cheongyi@iss.com", "Cheongyi");
			Student stud3 = new Student("Jess", "ISS", "jess@iss.com", "Jess");
			Student stud4 = new Student("Christine", "ISS", "christine@iss.com", "Christine");
			Student stud5 = new Student("Ariel", "ISS", "ariel@iss.com", "Ariel");
			Student stud6 = new Student("Channing", "ISS", "channing@iss.com", "Channing");
			Student stud7 = new Student("Jiayi", "ISS", "jiayi@iss.com", "Jiayi");
			Student stud8 = new Student("Xinji", "ISS", "xinji@iss.com", "Xinji");
			
			adminService.createStudent(stud1);
			adminService.createStudent(stud2);
			adminService.createStudent(stud3);
			adminService.createStudent(stud4);
			adminService.createStudent(stud5);
			adminService.createStudent(stud6);
			adminService.createStudent(stud7);
			adminService.createStudent(stud8);

			// create courses
			Course course1 = new Course();
			course1.setName("Foundation of Programming");
			course1.setDescription("Attain knowledge on basic programming and objected oriented programming with C#");
			course1.setCredits(4);
			course1.setCapacity(5);
			course1.setStartDate(LocalDate.now());
			course1.setEndDate(LocalDate.now().plusMonths(1));
			
			Course course2 = new Course();
			course2.setName("Design Digital Solution");
			course2.setDescription("Design software solutions which can support and integrate Business Intelligence");
			course2.setCredits(4);
			course2.setCapacity(10);
			course2.setStartDate(LocalDate.now());
			course2.setEndDate(LocalDate.now().plusMonths(3));
			
			Course course3 = new Course();
			course3.setName("Developing Web Application");
			course3.setDescription("Design enterprise Java EE web application using patterns and practices, resource constraints, connection pooling, etc");
			course3.setCredits(4);
			course3.setCapacity(10);
			course3.setStartDate(LocalDate.now());
			course3.setEndDate(LocalDate.now().plusMonths(1));

			Course course4 = new Course();
			course4.setName("Developing Machine Learning Solutions");
			course4.setDescription("Build and evaluate performance of machine learning models using Python");
			course4.setCredits(4);
			course4.setCapacity(4);
			course4.setStartDate(LocalDate.now());
			course4.setEndDate(LocalDate.now().plusMonths(1));

			Course course5 = new Course();
			course5.setName("Developing Mobile Solutions");
			course5.setDescription("Understand Android programming framework");
			course5.setCredits(4);
			course5.setCapacity(8);
			course5.setStartDate(LocalDate.now());
			course5.setEndDate(LocalDate.now().plusMonths(1));

			Course course6 = new Course();
			course6.setName("Capstone & Internship");
			course6.setDescription("Complete a one-month Capstone Project with end-to-end software development life cycle");
			course6.setCredits(4);
			course6.setCapacity(8);
			course6.setStartDate(LocalDate.now());
			course6.setEndDate(LocalDate.now().plusMonths(1));

			adminService.saveCourse(course1);
			adminService.saveCourse(course2);
			adminService.saveCourse(course3);
			adminService.saveCourse(course4);
			adminService.saveCourse(course5);
			adminService.saveCourse(course6);

			// add teachings to lecturer
			lect1.addTeaching(course1);
			lect1.addTeaching(course2);
			lect1.addTeaching(course3);

			lect2.addTeaching(course4);
			lect2.addTeaching(course1);
			lect2.addTeaching(course2);
			
			lect3.addTeaching(course3);
			lect3.addTeaching(course4);
			lect3.addTeaching(course1);

			lect4.addTeaching(course5);
			lect4.addTeaching(course6);
			
			adminService.saveLecturer(lect1);
			adminService.saveLecturer(lect2);
			adminService.saveLecturer(lect3);
			adminService.saveLecturer(lect4);

			// enrol students to course
			adminService.saveEnrollment(new Enrollment(stud1,course1,75));
			adminService.saveEnrollment(new Enrollment(stud1,course2,90));
			adminService.saveEnrollment(new Enrollment(stud1,course3));

			adminService.saveEnrollment(new Enrollment(stud2,course3,30));
			adminService.saveEnrollment(new Enrollment(stud2,course4));
			adminService.saveEnrollment(new Enrollment(stud2,course4,100));

			adminService.saveEnrollment(new Enrollment(stud3,course1,100));
			adminService.saveEnrollment(new Enrollment(stud3,course2,100));
			adminService.saveEnrollment(new Enrollment(stud3,course3));
			adminService.saveEnrollment(new Enrollment(stud3,course4,60));

			adminService.saveEnrollment(new Enrollment(stud4,course1,60));
			adminService.saveEnrollment(new Enrollment(stud4,course2,60));

			adminService.saveEnrollment(new Enrollment(stud5,course3,45));
			adminService.saveEnrollment(new Enrollment(stud5,course4,90));

			adminService.saveEnrollment(new Enrollment(stud6,course1,45));
			adminService.saveEnrollment(new Enrollment(stud6,course2,90));

			adminService.saveEnrollment(new Enrollment(stud7,course3,45));
			adminService.saveEnrollment(new Enrollment(stud7,course2,90));

			adminService.saveEnrollment(new Enrollment(stud8,course3,85));
			adminService.saveEnrollment(new Enrollment(stud8,course2,80));
		};
	}
}
