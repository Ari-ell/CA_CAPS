package com.team1.sa56.caps.controller;

import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Lecturer;
import com.team1.sa56.caps.service.CourseService;
import com.team1.sa56.caps.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/caps")
public class HomeController {
    
    @Autowired
    LecturerService lecturerService;
    
    @Autowired
    CourseService courseService;

    @GetMapping("/lecturers")
    public ResponseEntity<List<Lecturer>> lecturers(){
        List<Lecturer> lecturers = lecturerService.findAll();
       lecturers.stream().forEach(e->e.setPassword(""));
        ResponseEntity<List<Lecturer>>  response = new ResponseEntity<>(lecturers,HttpStatus.OK);
        return response;
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> courses(){
        List<Course> courses = courseService.findAll();
        courses.stream().forEach(course -> course.setEnrollments(null));
        ResponseEntity<List<Course>>  response = new ResponseEntity<>(courses,HttpStatus.OK);
        return response;
    }
}
