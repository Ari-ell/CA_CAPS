package com.team1.sa56.caps.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Course implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseCode = "C" + RandomStringUtils.randomNumeric(4);
	
	private String name;

    private String description;
	
	@Range(min = 1, max = 100, message = "Capacity has to be between  1 to 100")
    @Digits(integer = 100, fraction = 0, message = "Please input whole numbers only")
	private Integer capacity;
	
	@Range(min = 2, max = 10, message = "Credits must be between 2 and 10")
    @Digits(integer = 10, fraction = 0, message = "Please input whole numbers only")
	private Integer credits;
	
	@FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
    @FutureOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

    private boolean deleted = false;
    
    @JsonIgnore
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private Collection<Enrollment> enrollments;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "teaching")
    private Collection<Lecturer> lecturers;

    public Course(String name, String description, Integer credits, Integer capacity, LocalDate startDate, LocalDate endDate, 
                Collection<Enrollment> enrollments, Collection<Lecturer> lecturers){
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.capacity = capacity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollments = enrollments;
        this.lecturers = new ArrayList<Lecturer>();
        this.deleted = false;
    }

    public Course(String name, String description, Integer credits, Integer capacity, LocalDate startDate, LocalDate endDate){
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.capacity = capacity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollments = new ArrayList<Enrollment>();
        this.lecturers =  new ArrayList<Lecturer>();
        this.deleted = false;
    }

    public void addLecturer(Lecturer l){
        lecturers.add(l);
    }

    public void removeLecturer(Lecturer l){
        lecturers.remove(l);
    }    
    
    public void addEnrollment(Enrollment e){
        enrollments.add(e);
    }

    public void removeEnrollment(Enrollment e){
        enrollments.remove(e);
    }

    public Course(){
        super();
    }
    
}