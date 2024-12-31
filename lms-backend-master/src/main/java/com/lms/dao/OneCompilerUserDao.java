package com.lms.dao;

import com.lms.entity.OneCompilerUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OneCompilerUserDao extends JpaRepository<OneCompilerUser, Long> {

    Optional<OneCompilerUser> findByEmail(String email);
}
