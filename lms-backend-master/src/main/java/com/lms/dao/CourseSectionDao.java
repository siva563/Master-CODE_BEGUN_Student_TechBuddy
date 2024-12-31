package com.lms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.Course;
import com.lms.entity.CourseSection;

@Repository
public interface CourseSectionDao extends JpaRepository<CourseSection, Integer> {

	List<CourseSection> findByCourse(Course course);
	
}
