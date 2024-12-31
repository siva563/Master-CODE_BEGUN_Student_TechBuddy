package com.lms.dto;

import org.springframework.beans.BeanUtils;

import com.lms.entity.Address;
import com.lms.entity.MentorDetail;
import com.lms.entity.User;

import lombok.Data;

@Data
public class UserDto {

	private int id;

	private String firstName;

	private String lastName;

	private String emailId;

	private String phoneNo;

	private String role;

	private Address address;

	private MentorDetail mentorDetail;

	private String status;

	public static UserDto toUserDtoEntity(User user) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

}
