package com.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.CourseSectionTopic;

@Repository
public interface CourseSectionTopicDao extends JpaRepository<CourseSectionTopic, Integer> {

}
