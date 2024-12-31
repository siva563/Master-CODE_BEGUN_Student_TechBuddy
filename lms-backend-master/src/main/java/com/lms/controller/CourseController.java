package com.lms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.AddCourseRequestDto;
import com.lms.dto.AddCourseSectionRequestDto;
import com.lms.dto.AddCourseSectionTopicRequest;
import com.lms.dto.CommonApiResponse;
import com.lms.dto.CourseResponseDto;
import com.lms.dto.MentorDashboardDataResponse;
import com.lms.resource.CourseResource;
import com.lowagie.text.DocumentException;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/course")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

	@Autowired
	private CourseResource courseResource;

	@PostMapping("add")
	@Operation(summary = "Api to add the Mentor Course")
	public ResponseEntity<CourseResponseDto> addCourse(AddCourseRequestDto request) {
		return this.courseResource.addCourse(request);
	}

	@PostMapping("section/add")
	@Operation(summary = "Api to add the course section")
	public ResponseEntity<CourseResponseDto> addCourseSection(@RequestBody AddCourseSectionRequestDto request) {
		return this.courseResource.addCourseSection(request);
	}

	@PostMapping("section/topic/add")
	@Operation(summary = "Api to add the course section topic")
	public ResponseEntity<CourseResponseDto> addCourseSectionTopic(AddCourseSectionTopicRequest request) {
		return this.courseResource.addCourseSectionTopic(request);
	}

	@GetMapping("/fetch/course-id")
	@Operation(summary = "Api to fetch course by using course id")
	public ResponseEntity<CourseResponseDto> fetchCourseById(@RequestParam("courseId") Integer courseId,
			@RequestParam("videoShow") String videoShow) {
		return courseResource.fetchCourseById(courseId, videoShow);
	}

	@GetMapping("/fetch/course-user-id")
	@Operation(summary = "Api to fetch course by using course id and student id")
	public ResponseEntity<CourseResponseDto> fetchCourseById(@RequestParam("courseId") Integer courseId,
			@RequestParam("userId") Integer userId) {
		return courseResource.fetchCourseByIdAndUserId(courseId, userId);
	}

	@GetMapping("/fetch/status-wise")
	@Operation(summary = "Api to fetch courses by using status")
	public ResponseEntity<CourseResponseDto> fetchCoursesByStatus(@RequestParam("status") String status,
			@RequestParam("videoShow") String videoShow) {
		return courseResource.fetchCoursesByStatus(status, videoShow);
	}

	@GetMapping("/fetch/mentor-wise")
	@Operation(summary = "Api to fetch courses by using status")
	public ResponseEntity<CourseResponseDto> fetchCoursesByMentor(@RequestParam("mentorId") Integer mentorId,
			@RequestParam("status") String status, @RequestParam("videoShow") String videoShow) {
		return courseResource.fetchCoursesByMentor(mentorId, status, videoShow);
	}

	@GetMapping("/fetch/category-wise")
	@Operation(summary = "Api to fetch courses by using category")
	public ResponseEntity<CourseResponseDto> fetchCoursesByCategory(@RequestParam("categoryId") Integer categoryId,
			@RequestParam("videoShow") String videoShow) {
		return courseResource.fetchCoursesByCategory(categoryId, videoShow);
	}

	@GetMapping("/fetch/name-wise")
	@Operation(summary = "Api to fetch courses by using name")
	public ResponseEntity<CourseResponseDto> fetchCoursesByName(@RequestParam("courseName") String courseName) {
		return courseResource.fetchCoursesByName(courseName);
	}

	@DeleteMapping("/delete")
	@Operation(summary = "Api to delete the course")
	public ResponseEntity<CommonApiResponse> deleteCourse(@RequestParam("courseId") Integer courseId) {
		return courseResource.deleteCourse(courseId);
	}

	@GetMapping(value = "/{courseImageName}", produces = "image/*")
	public void fetchCourseImage(@PathVariable("courseImageName") String courseImageName, HttpServletResponse resp) {
		this.courseResource.fetchCourseImage(courseImageName, resp);
	}

	@GetMapping(value = "/video/{courseSectionTopicVideoFileName}", produces = "video/*")
	public void fetchCourseTopicVideo(
			@PathVariable("courseSectionTopicVideoFileName") String courseSectionTopicVideoFileName,
			HttpServletResponse resp) {
		this.courseResource.fetchCourseTopicVideo(courseSectionTopicVideoFileName, resp);
	}

	@GetMapping("notes/{notesFileName}/download")
	@Operation(summary = "Api for downloading the Course Notes")
	public ResponseEntity<Resource> downloadNotes(@PathVariable("notesFileName") String notesFileName,
			HttpServletResponse response) throws DocumentException, IOException {
		return this.courseResource.downloadNotes(notesFileName, response);
	}
	
	@GetMapping("/mentor/dashboard")
	@Operation(summary = "Api to fetch mentor dashboard data")
	public ResponseEntity<MentorDashboardDataResponse> fetchMentorDashboardData(@RequestParam("mentorId") Integer mentorId) {
		return courseResource.fetchMentorDashboardData(mentorId);
	}

}
