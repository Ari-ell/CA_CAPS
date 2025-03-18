package com.team1.sa56.caps.serviceImpl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.team1.sa56.caps.Util.Grade;
import com.team1.sa56.caps.model.Student;
import com.team1.sa56.caps.repository.StudentRepository;
import com.team1.sa56.caps.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentRepository studRepo;

    @Override
    public Student findById(Long id) {
        return studRepo.findById(id).get();
    }

    @Override
    public Student findByUsername(String username) {
        return studRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public List<Student> findAllStudents() {
        return studRepo.findAll();
    }

    @Override
    public Double getGpaFromGrades(List<Grade> gradeList) {
        Long count = gradeList.stream().filter(g -> g != null).count(); // get count of graded courses
        double totalGradePoint = gradeList.stream().filter(g -> g != null).mapToDouble(Grade::getGradePoint).sum();
        return count > 0 ? totalGradePoint/count : 0.0;
    }

    @Override
    public Student findStudentByEmail(String email) {
        return studRepo.findByEmail(email);
    }

    @Override
    public List<Student> findAll() {
        return studRepo.findAll();
    }
}
