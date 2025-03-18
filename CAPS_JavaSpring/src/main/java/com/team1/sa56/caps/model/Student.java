package com.team1.sa56.caps.model;

import java.util.Collection;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
public class Student extends User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z]*", message = "First Name cannot contain numbers or special characters")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]*", message = "First Name cannot contain numbers or special characters")
    private String lastName;

    private String username = "S" + RandomStringUtils.randomNumeric(5);

    @Email(message = "Please enter a valid email format")
    private String email;

    @Column(unique = true)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Collection<Enrollment> enrollments;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate enrollmentDate;

    public Student(){
        super();
    };

    public Student(String firstName, String lastName, String email, String password, LocalDate enrollmentDate){
        super(email,password,"Student");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enrollmentDate = enrollmentDate;
        this.enrollments = new ArrayList<Enrollment>();
    }

    public Student(String firstName, String lastName, String email, String password){
        super(email,password,"Student");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enrollments = new ArrayList<Enrollment>();
        this.enrollmentDate = LocalDate.now();
    }
}
