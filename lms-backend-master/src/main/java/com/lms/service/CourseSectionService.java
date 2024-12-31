package com.lms.service;

import java.util.List;

import com.lms.entity.Course;
import com.lms.entity.CourseSection;

public interface CourseSectionService {

	CourseSection add(CourseSection section);
	
	CourseSection getById(int section);
	
	List<CourseSection> getByCourse(Course course);
	
}
