package com.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.Student;
import java.util.List;
import java.util.Optional;


@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
    Optional<Student> findByEmail(String email);
}
