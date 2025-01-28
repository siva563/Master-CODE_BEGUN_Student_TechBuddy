package com.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.entity.PasswordResetToken;

public interface PasswordResetTokenDao extends JpaRepository<PasswordResetToken, Long> {
    void deleteByStudentId(Long studentId);

}
