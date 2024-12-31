package com.lms.dto;

import java.util.ArrayList;
import java.util.List;

import com.lms.entity.Course;

import lombok.Data;

@Data
public class CourseResponseDto extends CommonApiResponse {

	private List<Course> courses = new ArrayList<>();
	
	private Course course;
	
	private String isCoursePurchased;
	
}
