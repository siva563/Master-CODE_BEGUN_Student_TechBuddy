package com.lms.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddCourseSectionTopicRequest {

	private int sectionId;

	private String srNo;

	private String name;

	private String description;

	private MultipartFile video;
	
}
