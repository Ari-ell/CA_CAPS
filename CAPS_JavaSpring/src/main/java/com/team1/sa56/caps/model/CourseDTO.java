package com.team1.sa56.caps.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {
    private Long id;
    private String courseCode;
    private String name;
    private String description;
    private Integer capacity;
    private Integer credits;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfStudentsEnrolled;

    private int numberOfLecturersTeaching;

    private List<String> lecturerNames;
    
    public CourseDTO(Course course, int numberOfStudentsEnrolled, int numberOfLecturersTeaching, List<String> lecturerNames) {
        this.id = course.getId();
        this.courseCode = course.getCourseCode();
        this.name = course.getName();
        this.description = course.getDescription();
        this.capacity = course.getCapacity();
        this.credits = course.getCredits();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
        this.numberOfStudentsEnrolled = numberOfStudentsEnrolled;
        this.numberOfLecturersTeaching = numberOfLecturersTeaching;
        this.lecturerNames = lecturerNames;
    }

    // Optional: Override toString() for easy debugging or logging

    // Optional: Add any other methods as needed
}
