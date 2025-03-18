package com.team1.sa56.caps.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
public class Admin extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Pattern(regexp = "^[a-zA-Z]*", message = "First Name cannot contain numbers or special characters")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]*", message = "Last Name cannot contain numbers or special characters")
    private String lastName;

    private String username = "A" + RandomStringUtils.randomNumeric(5);

    @Email(message = "Please enter a valid email format")
    private String email;

    @Column(unique = true)
    private String password;

    public Admin(){
        super();
    };

    public Admin(String firstName, String lastName, String email, String password){
        super(email,password,"Admin");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
