package com.team1.sa56.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team1.sa56.caps.model.Student;


public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s from Student s WHERE s.username= :username")
    public Student findByUsername(@Param("username") String username);

    @Query("SELECT s from Student s WHERE s.email= :email")
    public Student findByEmail(@Param("email") String email);
    
    // public List<Enrollment> findStudentEnrollNotApproved
}
