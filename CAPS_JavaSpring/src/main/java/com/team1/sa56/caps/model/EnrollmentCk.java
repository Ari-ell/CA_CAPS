package com.team1.sa56.caps.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class EnrollmentCk implements Serializable{
    
    private Long course;
    private Long student;

    public EnrollmentCk(Long course, Long student){
        this.course = course;
        this.student = student;
    }

    public EnrollmentCk(){};
}
