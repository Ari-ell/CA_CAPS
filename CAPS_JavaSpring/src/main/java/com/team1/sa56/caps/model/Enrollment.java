package com.team1.sa56.caps.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@IdClass(EnrollmentCk.class)
public class Enrollment implements Serializable{

    @JsonIgnore
    @Id
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Student student;

    @JsonIgnore
    @Id
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private Course course;

    @Range(min = 0, max = 100)
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('APPROVED', 'PENDING', 'REJECTED') default 'APPROVED'")
    private EnrollmentStatusEnum enrollmentStatus;

    private boolean deleted;

    public Enrollment(Student student, Course course, Integer score, EnrollmentStatusEnum enrollmentStatus){
        this.student = student;
        this.course = course;
        this.score = score;
        this.enrollmentStatus = enrollmentStatus;
    }

    public Enrollment(Student student, Course course){
        super();
        this.student = student;
        this.course = course;
        this.enrollmentStatus = EnrollmentStatusEnum.APPROVED;
    }

    public Enrollment(Student student, Course course, Integer score){
        super();
        this.student = student;
        this.course = course;
        this.score = score;
        this.enrollmentStatus = EnrollmentStatusEnum.APPROVED;
    }

    public Enrollment(){
        super();
    }
}