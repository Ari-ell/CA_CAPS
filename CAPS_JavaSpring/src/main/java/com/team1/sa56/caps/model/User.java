package com.team1.sa56.caps.model;

import lombok.NoArgsConstructor;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
  
    @Email(message = "Must be a valid email address")
    // @Pattern(regexp = "/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$/", message = "please enter a valid email format")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String Role;

    public User(String email, String password, String Role) {
        this.email = email;
        this.password = password;
        this.Role = Role;
    }
}
