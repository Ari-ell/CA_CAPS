package com.team1.sa56.caps.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team1.sa56.caps.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
	
	@Query("SELECT c FROM Course c WHERE c.id = :id")
	public Course findCourseById(@Param("id") Long id);
	
	@Query("SELECT c FROM Course c WHERE c.courseCode = :code")
	public Course findByCourseCode(@Param("code") String courseCode);

	@Query("SELECT c FROM Course c")
	public List<Course> findAllCourses();

	@Modifying
    @Query("UPDATE Course c SET c.deleted = true WHERE c.id = :id")
    void softDeleteCourse(@Param("id") String id);

 	Page<Course> findByDeletedFalse(Pageable pageable);

	@Query("SELECT c FROM Course c JOIN FETCH c.lecturers")
    public List<Course> findAllWithLecturers();

	@Query("SELECT c FROM Course c WHERE c NOT IN (:courses)")
	public List<Course> findCourseNotEnrolByStudent(@Param("courses") List<Course> stuCourseList);
}
