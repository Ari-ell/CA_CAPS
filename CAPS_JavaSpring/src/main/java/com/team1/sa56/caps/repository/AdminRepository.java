package com.team1.sa56.caps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team1.sa56.caps.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

    public List<Admin> findByUsername(String username);

    // public List<Admin> findByUsernameAndPassword(String username, String password);

	@Query("SELECT a FROM Admin a")
	public List<Admin> findAllAdmin();
}
