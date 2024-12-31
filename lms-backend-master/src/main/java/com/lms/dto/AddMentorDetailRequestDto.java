package com.lms.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lms.entity.MentorDetail;

import lombok.Data;

@Data
public class AddMentorDetailRequestDto {

	private int id;

	private int age;

	private String bio;

	private String highestQualification; // B Tech, B Pharm

	private String profession;

	private double experience;

	private MultipartFile profilePic;
	
	private int mentorId;

	public static MentorDetail toEntity(AddMentorDetailRequestDto addMentorDetailRequestDto) {
		MentorDetail mentorDetail = new MentorDetail();
		BeanUtils.copyProperties(addMentorDetailRequestDto, mentorDetail, "profilePic", "mentorId", "id");
		return mentorDetail;
	}

}
