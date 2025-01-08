package com.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
}
