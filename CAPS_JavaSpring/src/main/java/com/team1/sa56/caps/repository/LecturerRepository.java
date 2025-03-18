package com.team1.sa56.caps.repository;

import com.team1.sa56.caps.model.Lecturer;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {
	
	@Query("SELECT l from Lecturer l WHERE l.username= :username")
	public Lecturer findByUsername(@Param("username") String username);

	@Query("SELECT l from Lecturer l WHERE l.id= :id")
	public Lecturer findByLecturerId(@Param("id") Long lecturerId);

	@Query("SELECT l from Lecturer l WHERE l.email=:email")
	public Lecturer findByEmail(@Param("email") String email);

}
