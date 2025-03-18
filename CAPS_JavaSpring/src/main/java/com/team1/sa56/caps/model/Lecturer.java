package com.team1.sa56.caps.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Email;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
public class Lecturer extends User{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Pattern(regexp = "^[a-zA-Z]*", message = "First Name cannot contain numbers or special characters")
    private String firstName;
    
    @Pattern(regexp = "^[a-zA-Z]*", message = "First Name cannot contain numbers or special characters")
    private String lastName;

    @Column(columnDefinition = "nvarchar(200)")
    private String description;
    
    @Column
    private String username = "L" + RandomStringUtils.randomNumeric(5);
    
    @Column(unique = true)
    private String password;

    @Email(message = "Please enter a valid email format")
    private String email;
    
    private boolean deleted;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "lecturer_course", joinColumns = @JoinColumn(name = "lecturer_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Collection<Course> teaching;

    public Lecturer(String firstName, String lastName, String description,  String password, String email, Collection<Course> teaching){
        super(email,password,"Lecturer");
        this.firstName = firstName;
        this.lastName=lastName;
        this.description = description;
        this.password = password;
        this.email=email;
        this.teaching = teaching;
    }
    
    public Lecturer(String firstName, String lastName, String email, String password){
        super(email,password,"Lecturer");
        this.firstName = firstName;
        this.lastName=lastName;
        this.password = password;
        this.email=email;
        this.teaching = new ArrayList<Course>();
    }
    
    public Lecturer(){
        super();
    }
    
    //added
       public void generateUsername() {
        this.username = "L" + RandomStringUtils.randomNumeric(5);
    }

    public void addTeaching(Course c){
        teaching.add(c);
    }

    public void removeTeaching(Course c){
        teaching.remove(c);
    }
}