package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lms.dto.AddMentorDetailRequestDto;
import com.lms.dto.CommonApiResponse;
import com.lms.dto.RegisterUserRequestDto;
import com.lms.dto.UserLoginRequest;
import com.lms.dto.UserLoginResponse;
import com.lms.dto.UserResponseDto;
import com.lms.resource.UserResource;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserResource userResource;

	// RegisterUserRequestDto, we will set only email, password & role from UI
	@PostMapping("/admin/register")
	@Operation(summary = "Api to register Admin")
	private ResponseEntity<CommonApiResponse> registerAdmin(@RequestBody RegisterUserRequestDto request) {
		return userResource.registerAdmin(request);
	}

	// for customer and tour guide register
	@PostMapping("register")
	@Operation(summary = "Api to register customer or seller user")
	public ResponseEntity<CommonApiResponse> registerUser(@RequestBody RegisterUserRequestDto request) {
		return this.userResource.registerUser(request);
	}

	@PutMapping("mentor/detail/update")
	@Operation(summary = "Api to update the mentor detail")
	public ResponseEntity<CommonApiResponse> addMentorDetail(AddMentorDetailRequestDto request) {
		return this.userResource.addMentorDetail(request);
	}

	@PostMapping("login")
	@Operation(summary = "Api to login any User")
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
		return userResource.login(userLoginRequest);
	}

	@GetMapping("/fetch/role-wise")
	@Operation(summary = "Api to get Users By Role")
	public ResponseEntity<UserResponseDto> fetchAllUsersByRole(@RequestParam("role") String role)
			throws JsonProcessingException {
		return userResource.getUsersByRole(role);
	}

	@DeleteMapping("/mentor/delete")
	@Operation(summary = "Api to delete the mentor and all it's course")
	public ResponseEntity<CommonApiResponse> deleteMentor(@RequestParam("mentorId") Integer mentorId) {
		return userResource.deleteMentor(mentorId);
	}

	@GetMapping("/fetch/user-id")
	@Operation(summary = "Api to get User Detail By User Id")
	public ResponseEntity<UserResponseDto> fetchUserById(@RequestParam("userId") int userId) {
		return userResource.getUserById(userId);
	}
	
	@GetMapping(value = "/{userImageName}", produces = "image/*")
	public void fetchTourImage(@PathVariable("userImageName") String userImageName, HttpServletResponse resp) {
		this.userResource.fetchUserImage(userImageName, resp);
	}

}
