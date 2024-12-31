package com.lms.dto;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lms.entity.Course;

import lombok.Data;

@Data
public class AddCourseRequestDto {

	private int id;

	private int mentorId;

	private int categoryId;

	private String name;

	private String description;

	private String type; // free, paid

	private BigDecimal fee;
	
	private int discountInPercent;

	private String authorCourseNote;

	private String specialNote;

	private String prerequisite;

	private MultipartFile notesFileName;

	private MultipartFile thumbnail;

	public static Course toEntity(AddCourseRequestDto dto) {
		Course course = new Course();
		BeanUtils.copyProperties(dto, course, "id", "mentorId", "categoryId", "notesFileName", "thumbnail");
		return course;
	}

}
