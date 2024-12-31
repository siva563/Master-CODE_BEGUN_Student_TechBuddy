package com.lms.resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;

import com.lms.dto.AddCourseRequestDto;
import com.lms.dto.AddCourseSectionRequestDto;
import com.lms.dto.AddCourseSectionTopicRequest;
import com.lms.dto.CommonApiResponse;
import com.lms.dto.CourseResponseDto;
import com.lms.dto.MentorDashboardDataResponse;
import com.lms.entity.Booking;
import com.lms.entity.Category;
import com.lms.entity.Course;
import com.lms.entity.CourseSection;
import com.lms.entity.CourseSectionTopic;
import com.lms.entity.User;
import com.lms.service.BookingService;
import com.lms.service.CategoryService;
import com.lms.service.CourseSectionService;
import com.lms.service.CourseSectionTopicService;
import com.lms.service.CourseService;
import com.lms.service.StorageService;
import com.lms.service.UserService;
import com.lms.utility.Constants.ActiveStatus;
import com.lms.utility.Constants.CoursePurchased;
import com.lms.utility.Constants.CourseTopicVideoShow;
import com.lms.utility.Constants.CourseType;
import com.lms.utility.Constants.UserRole;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CourseResource {

	private final Logger LOG = LoggerFactory.getLogger(CourseResource.class);

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseSectionService courseSectionService;

	@Autowired
	private CourseSectionTopicService courseSectionTopicService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private BookingService bookingService;

	public ResponseEntity<CourseResponseDto> addCourse(AddCourseRequestDto request) {

		LOG.info("received request for adding the mentor course");

		CourseResponseDto response = new CourseResponseDto();

		if (request == null) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getCategoryId() == 0 || request.getDescription() == null || request.getMentorId() == 0
				|| request.getName() == null || request.getNotesFileName() == null || request.getType() == null
				|| request.getThumbnail() == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		String addedDateTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		Category category = this.categoryService.getCategoryById(request.getCategoryId());

		if (category == null) {
			response.setResponseMessage("category not found");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		User mentor = this.userService.getUserById(request.getMentorId());

		if (mentor == null) {
			response.setResponseMessage("mentor not found");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Course course = AddCourseRequestDto.toEntity(request);

		String courseNote = this.storageService.storeCourseNote(request.getNotesFileName());
		String thumbnailFilename = this.storageService.storeCourseNote(request.getThumbnail());

		course.setThumbnail(thumbnailFilename);
		course.setAddedDateTime(addedDateTime);
		course.setNotesFileName(courseNote);
		course.setCategory(category);
		course.setMentor(mentor);
		course.setStatus(ActiveStatus.ACTIVE.value());

		Course savedCourse = this.courseService.add(course);

		if (savedCourse == null) {
			response.setResponseMessage("Failed to add the course");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			response.setCourse(savedCourse);
			response.setResponseMessage("Course Created Successful, Add Course Section now....");
			response.setSuccess(true);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<CourseResponseDto> addCourseSection(AddCourseSectionRequestDto request) {

		LOG.info("received request for adding the course section");

		CourseResponseDto response = new CourseResponseDto();

		if (request == null) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getCourseId() == 0 || request.getName() == null || request.getDescription() == null
				|| request.getSrNo() == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Course course = this.courseService.getById(request.getCourseId());

		if (course == null) {
			response.setResponseMessage("course not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		CourseSection section = new CourseSection();

		section.setCourse(course);
		section.setDescription(request.getDescription());
		section.setName(request.getName());
		section.setSrNo(request.getSrNo());

		CourseSection savedSection = this.courseSectionService.add(section);

		if (savedSection == null) {
			response.setCourse(course);
			response.setResponseMessage("Failed to add the course section");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {

			Course updatedCourse = this.courseService.getById(request.getCourseId());

			response.setCourse(updatedCourse);
			response.setResponseMessage("Course Section Added successful!!!");
			response.setSuccess(true);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<CourseResponseDto> addCourseSectionTopic(AddCourseSectionTopicRequest request) {

		LOG.info("received request for adding the course section");

		CourseResponseDto response = new CourseResponseDto();

		if (request == null) {
			response.setResponseMessage("missing request body");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getSectionId() == 0 || request.getName() == null || request.getDescription() == null
				|| request.getSrNo() == null || request.getVideo() == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		CourseSection section = this.courseSectionService.getById(request.getSectionId());

		if (section == null) {
			response.setResponseMessage("Course Section not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		CourseSectionTopic topic = new CourseSectionTopic();
		topic.setName(request.getName());
		topic.setSrNo(request.getSrNo());
		topic.setDescription(request.getDescription());
		topic.setCourseSection(section);

		String topicVideoFileName = this.storageService.storeCourseVideo(request.getVideo());

		topic.setVideoFileName(topicVideoFileName);

		CourseSectionTopic savedTopic = this.courseSectionTopicService.add(topic);

		Course updatedCourse = this.courseService.getById(section.getCourse().getId());

		if (savedTopic == null) {
			response.setCourse(updatedCourse);
			response.setResponseMessage("Failed to add the course section topic");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			response.setCourse(updatedCourse);
			response.setResponseMessage("Course Section Topic Added successful!!!");
			response.setSuccess(true);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);
		}

	}

	public ResponseEntity<CourseResponseDto> fetchCourseById(Integer courseId, String toShowVideo) {

		LOG.info("received request for fetching the course by using id");

		CourseResponseDto response = new CourseResponseDto();

		if (courseId == null || courseId == 0) {
			response.setResponseMessage("missing course id");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Course course = this.courseService.getById(courseId);

		if (course == null) {
			response.setResponseMessage("course not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<CourseSection> sections = course.getSections();

		if (toShowVideo.equals(CourseTopicVideoShow.NO.value())) {
			if (!CollectionUtils.isEmpty(sections)) {

				for (CourseSection section : sections) {

					List<CourseSectionTopic> topics = section.getCourseSectionTopics();

					if (!CollectionUtils.isEmpty(topics)) {

						for (CourseSectionTopic topic : topics) {
							topic.setVideoFileName("");
						}

					}

				}

			}
		}

		response.setCourse(course);
		response.setResponseMessage("Course Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<CourseResponseDto> fetchCoursesByStatus(String status, String videoShow) {

		LOG.info("received request for fetching the courses by status");

		CourseResponseDto response = new CourseResponseDto();

		if (status == null || videoShow == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Course> courses = this.courseService.getByStatus(status);

		if (CollectionUtils.isEmpty(courses)) {
			response.setResponseMessage("Courses not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);
		}

		if (videoShow.equals(CourseTopicVideoShow.NO.value())) {

			for (Course course : courses) {
				List<CourseSection> sections = course.getSections();
				if (!CollectionUtils.isEmpty(sections)) {

					for (CourseSection section : sections) {

						List<CourseSectionTopic> topics = section.getCourseSectionTopics();

						if (!CollectionUtils.isEmpty(topics)) {

							for (CourseSectionTopic topic : topics) {
								topic.setVideoFileName("");
							}

						}

					}

				}
			}

		}

		response.setCourses(courses);
		response.setResponseMessage("Courses Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<CourseResponseDto> fetchCoursesByMentor(Integer mentorId, String status, String videoShow) {

		LOG.info("received request for fetching the courses by mentor and status");

		CourseResponseDto response = new CourseResponseDto();

		if (mentorId == null || mentorId == 0 || status == null || videoShow == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		User mentor = this.userService.getUserById(mentorId);

		if (mentor == null || !mentor.getRole().equals(UserRole.ROLE_MENTOR.value())) {
			response.setResponseMessage("mentor not found");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Course> courses = this.courseService.getByMentorAndStatus(mentor, status);

		if (CollectionUtils.isEmpty(courses)) {
			response.setResponseMessage("Courses not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);
		}

		if (videoShow.equals(CourseTopicVideoShow.NO.value())) {

			for (Course course : courses) {
				List<CourseSection> sections = course.getSections();
				if (!CollectionUtils.isEmpty(sections)) {

					for (CourseSection section : sections) {

						List<CourseSectionTopic> topics = section.getCourseSectionTopics();

						if (!CollectionUtils.isEmpty(topics)) {

							for (CourseSectionTopic topic : topics) {
								topic.setVideoFileName("");
							}

						}

					}

				}
			}

		}

		response.setCourses(courses);
		response.setResponseMessage("Courses Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<CourseResponseDto> fetchCoursesByCategory(Integer categoryId, String videoShow) {

		LOG.info("received request for fetching the courses by mentor and status");

		CourseResponseDto response = new CourseResponseDto();

		if (categoryId == null || categoryId == 0 || videoShow == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Category category = this.categoryService.getCategoryById(categoryId);

		if (category == null) {
			response.setResponseMessage("category not found");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Course> courses = this.courseService.getByCategoryAndStatus(category, ActiveStatus.ACTIVE.value());

		if (CollectionUtils.isEmpty(courses)) {
			response.setResponseMessage("Courses not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);
		}

		if (videoShow.equals(CourseTopicVideoShow.NO.value())) {

			for (Course course : courses) {
				List<CourseSection> sections = course.getSections();
				if (!CollectionUtils.isEmpty(sections)) {

					for (CourseSection section : sections) {

						List<CourseSectionTopic> topics = section.getCourseSectionTopics();

						if (!CollectionUtils.isEmpty(topics)) {

							for (CourseSectionTopic topic : topics) {
								topic.setVideoFileName("");
							}

						}

					}

				}
			}

		}

		response.setCourses(courses);
		response.setResponseMessage("Courses Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<CourseResponseDto> fetchCoursesByName(String courseName) {

		LOG.info("received request for fetching the courses by mentor and status");

		CourseResponseDto response = new CourseResponseDto();

		if (courseName == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Course> courses = this.courseService.getByNameAndStatus(courseName, ActiveStatus.ACTIVE.value());

		if (CollectionUtils.isEmpty(courses)) {
			response.setResponseMessage("Courses not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);
		}

		for (Course course : courses) {
			List<CourseSection> sections = course.getSections();
			if (!CollectionUtils.isEmpty(sections)) {

				for (CourseSection section : sections) {

					List<CourseSectionTopic> topics = section.getCourseSectionTopics();

					if (!CollectionUtils.isEmpty(topics)) {

						for (CourseSectionTopic topic : topics) {
							topic.setVideoFileName("");
						}

					}

				}

			}
		}

		response.setCourses(courses);
		response.setResponseMessage("Courses Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);

	}

	public void fetchCourseImage(String courseImageName, HttpServletResponse resp) {
		Resource resource = storageService.loadCourseNote(courseImageName);
		if (resource != null) {
			try (InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void fetchCourseTopicVideo(String courseSectionTopicVideoFileName, HttpServletResponse resp) {
		Resource resource = storageService.loadCourseVideo(courseSectionTopicVideoFileName);
		if (resource != null && resource.exists()) {
			try (InputStream in = resource.getInputStream(); ServletOutputStream out = resp.getOutputStream()) {
				resp.setContentType("video/mp4");
				FileCopyUtils.copy(in, out);
				out.flush();
			} catch (IOException e) {

				LOG.info("Video Player closed or any netwrok issue!!!");

				e.printStackTrace();
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

			}
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	public ResponseEntity<CourseResponseDto> fetchCourseByIdAndUserId(Integer courseId, Integer userId) {

		LOG.info("received request for fetching the course by id and used id");

		CourseResponseDto response = new CourseResponseDto();

		if (courseId == null || courseId == 0) {
			response.setResponseMessage("missing course id");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		Course course = this.courseService.getById(courseId);

		if (course == null) {
			response.setResponseMessage("course not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CourseResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<CourseSection> sections = course.getSections();

		if (!CollectionUtils.isEmpty(sections)) {

			for (CourseSection section : sections) {

				List<CourseSectionTopic> topics = section.getCourseSectionTopics();

				if (!CollectionUtils.isEmpty(topics)) {

					for (CourseSectionTopic topic : topics) {
						topic.setVideoFileName("");
					}

				}

			}

		}

		User student = null;

		if (userId > 0 && course.getType().equals(CourseType.PAID.value())) {
			student = this.userService.getUserById(userId);

			if (student != null) {

				List<Booking> bookings = this.bookingService.getByCourseAndCustomer(course, student);

				if (!CollectionUtils.isEmpty(bookings)) {
					response.setIsCoursePurchased(CoursePurchased.YES.value());
				} else {
					response.setIsCoursePurchased(CoursePurchased.NO.value());
				}

			}

		}

		response.setCourse(course);
		response.setResponseMessage("Course Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CourseResponseDto>(response, HttpStatus.OK);

	}

	public ResponseEntity<CommonApiResponse> deleteCourse(Integer courseId) {

		LOG.info("received request for fetching the course by using id");

		CommonApiResponse response = new CommonApiResponse();

		if (courseId == null || courseId == 0) {
			response.setResponseMessage("missing course id");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Course course = this.courseService.getById(courseId);

		if (course == null) {
			response.setResponseMessage("course not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		course.setStatus(ActiveStatus.DEACTIVATED.value());
		this.courseService.update(course);

		response.setResponseMessage("Course Deleted Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<Resource> downloadNotes(String notesFileName, HttpServletResponse response) {

		Resource resource = storageService.loadCourseNote(notesFileName);
		if (resource == null) {
			// Handle file not found
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Course_Notes\"")
				.body(resource);

	}

	public ResponseEntity<MentorDashboardDataResponse> fetchMentorDashboardData(Integer mentorId) {

		LOG.info("received request for fetching the mentor dashboard data");

		MentorDashboardDataResponse response = new MentorDashboardDataResponse();

		if (mentorId == null || mentorId == 0) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<MentorDashboardDataResponse>(response, HttpStatus.BAD_REQUEST);
		}

		User mentor = this.userService.getUserById(mentorId);

		if (mentor == null || !mentor.getRole().equals(UserRole.ROLE_MENTOR.value())) {
			response.setResponseMessage("mentor not found");
			response.setSuccess(false);

			return new ResponseEntity<MentorDashboardDataResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Long totalActiveCourse = courseService.getCountByMentorAndStatus(mentor, ActiveStatus.ACTIVE.value());
		Long totalDeactivatedCourse = courseService.getCountByMentorAndStatus(mentor, ActiveStatus.DEACTIVATED.value());

		List<Booking> bookings = this.bookingService.getByMentor(mentor);

		response.setBookings(!CollectionUtils.isEmpty(bookings) ? bookings : new ArrayList<>());
		response.setTotalCoursePurchases(!CollectionUtils.isEmpty(bookings) ? bookings.size() : 0);

		response.setTotalActiveCourse(totalActiveCourse != null ? totalActiveCourse : 0);
		response.setTotalDeletedCourse(totalDeactivatedCourse != null ? totalDeactivatedCourse : 0);
		response.setTotalPurchaseAmount(mentor.getAmount());
		response.setResponseMessage("Dashboard Data Fetched Successful!!!");
		response.setSuccess(true);

		return new ResponseEntity<MentorDashboardDataResponse>(response, HttpStatus.OK);

	}

}
