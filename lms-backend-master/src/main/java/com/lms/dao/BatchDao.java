package com.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.entity.Batch;

public interface BatchDao extends JpaRepository<Batch, Long> {
}