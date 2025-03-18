package com.team1.sa56.caps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team1.sa56.caps.model.Course;
import com.team1.sa56.caps.model.Enrollment;
import com.team1.sa56.caps.model.EnrollmentCk;
import com.team1.sa56.caps.model.EnrollmentStatusEnum;
import com.team1.sa56.caps.model.Student;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentCk>{

    @Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus = 'APPROVED'")
    public List<Enrollment> findEnrolByApprovedStatus(EnrollmentStatusEnum enrollmentStatus);

    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :id")
	public List<Enrollment> findEnrolByStudentId(@Param("id") int id);

    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :stu AND e.course.id = :course")
	public Enrollment findEnrolByStudentCourse(@Param("stu") Long studentId, @Param("course") Long courseId);
		
	@Query("Select e.course.id from Enrollment e where e.student.id = :id")
	public List<Integer> findCourseIdByStudentId(@Param("id") Integer studentId);

	@Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :id")
	public List<Student> findStudentsByCourseId(@Param("id") Long id);
	
	@Query("SELECT e.score FROM Enrollment e WHERE e.course.id = :id")
	public List<Integer> findMarksByCourseId(@Param("id") Long id);
	
	@Query("SELECT e FROM Enrollment e WHERE e.student.id = :id")
	public List<Enrollment> findEnrolByStudentId(@Param("id") Long id);
	
	@Query("SELECT e.score FROM Enrollment e WHERE e.student.id = :id")
	public List<Integer> findMarksByStudentId(@Param("id") Long id);
	
	@Query("SELECT e FROM Enrollment e WHERE e.course.courseCode = :code")
    public List<Enrollment> findByCourseCode(@Param("code") String code);
		
	@Query("Select e.course.id from Enrollment e where e.student.id = :id")
	public List<Integer> findCourseIdByStudentId(@Param("id") Long id);
	
	@Query("SELECT e FROM Enrollment e WHERE e.course.id = :id")
	public List<Enrollment> findEnrolByCourseId(@Param("id") Long id);

	@Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus = 'APPROVED' AND e.course.id = :id")
	public List<Enrollment> getApprovedEnrolByCourseId(@Param("id") Long courseId);

	@Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus = 'APPROVED' AND e.student.id = :id")
	public List<Enrollment> getApprovedEnrolByStudentId(@Param("id") Long studentId);

	@Query("Select e.course from Enrollment e where e.student.id = :id")
	public List<Course> findCourseByStudentId(@Param("id") Long stuId);
	
	@Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus ='PENDING'")
    public List<Enrollment> findPendingRequest();

	@Query("SELECT e.student FROM Enrollment e WHERE e.enrollmentStatus = 'APPROVED' AND e.course.id = :id")
	public List<Student>findApproveStudentsByCourse(@Param("id") Long id);

	@Query("SELECT e FROM Enrollment e WHERE e.course.id = :id AND (e.enrollmentStatus = 'APPROVED' OR e.enrollmentStatus = 'PENDING')")
	public List<Enrollment> findEnrolByCourseNotRejected(@Param("id") Long courseId);

	@Query("SELECT e FROM Enrollment e WHERE e.enrollmentStatus = 'REJECTED'")
	public List<Enrollment> findRejectedStudent();
}
