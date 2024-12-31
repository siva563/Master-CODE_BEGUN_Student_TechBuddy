package com.lms.service;

import java.util.List;

import com.lms.entity.Category;
import com.lms.entity.Course;
import com.lms.entity.User;

public interface CourseService {

	Course add(Course course);
	
	Course update(Course course);
	
	List<Course> updateAll(List<Course> courses);

	Course getById(int id);

	List<Course> getAll();

	List<Course> getByStatus(String status);

	List<Course> getByMentorAndStatus(User mentor, String status);
	
	List<Course> getByMentor(User mentor);
	
	List<Course> getByCategoryAndStatus(Category category, String status);
	
	List<Course> getByNameAndStatus(String name, String status);
	
	Long getCountByMentorAndStatus(User mentor, String status);


}
