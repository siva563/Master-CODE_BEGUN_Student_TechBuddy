package com.lms.resource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.lms.dto.BookingRequestDto;
import com.lms.dto.BookingResponseDto;
import com.lms.dto.CommonApiResponse;
import com.lms.entity.Booking;
import com.lms.entity.Course;
import com.lms.entity.Payment;
import com.lms.entity.User;
import com.lms.service.BookingService;
import com.lms.service.CourseService;
import com.lms.service.PaymentService;
import com.lms.service.UserService;
import com.lms.utility.Constants.BookingStatus;
import com.lms.utility.Constants.CoursePurchased;
import com.lms.utility.Helper;

@Component
public class BookingResource {

	private final Logger LOG = LoggerFactory.getLogger(BookingResource.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

	public ResponseEntity<CommonApiResponse> addBooking(BookingRequestDto request) {

		LOG.info("request received for adding customer booking course");

		CommonApiResponse response = new CommonApiResponse();

		String bookingTime = String
				.valueOf(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

		if (request == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getCourseId() == 0 || request.getCustomerId() == 0 || request.getCvv() == null
				|| request.getExpiryDate() == null || request.getNameOnCard() == null || request.getCardNo() == null
				|| request.getAmount() == null) {

			response.setResponseMessage("missing input or invalid details");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);

		}

		User customer = this.userService.getUserById(request.getCustomerId());

		if (customer == null) {
			response.setResponseMessage("customer not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		Course course = this.courseService.getById(request.getCourseId());

		if (course == null) {
			response.setResponseMessage("course not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}
		
		List<Booking> bookings = this.bookingService.getByCourseAndCustomer(course, customer);

		if (!CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("Course Already Purchased!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.BAD_REQUEST);
		}

		String bookingId = Helper.generateTourBookingId();
		String paymentBookingId = Helper.generateBookingPaymentId();

		Payment payment = new Payment();
		payment.setCardNo(request.getCardNo());
		payment.setBookingId(bookingId);
		payment.setAmount(request.getAmount());
		payment.setCustomer(customer);
		payment.setCvv(request.getCvv());
		payment.setExpiryDate(request.getExpiryDate());
		payment.setNameOnCard(request.getNameOnCard());
		payment.setPaymentId(paymentBookingId);

		Payment savedPayment = this.paymentService.addPayment(payment);

		if (savedPayment == null) {
			response.setResponseMessage("Failed to Purchase the Course, Payment Failure!!!");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		User mentor = course.getMentor();
		mentor.setAmount(mentor.getAmount().add(request.getAmount()));

		this.userService.updateUser(mentor);

		Booking booking = new Booking();
		booking.setBookingId(bookingId);
		booking.setPayment(savedPayment);
		booking.setAmount(request.getAmount());
		booking.setBookingTime(bookingTime);
		booking.setCustomer(customer);
		booking.setCourse(course);
		booking.setStatus(BookingStatus.CONFIRMED.value());

		Booking savedBooking = this.bookingService.addBooking(booking);

		if (savedBooking == null) {
			response.setResponseMessage("Failed to Book Event, Internal Error");
			response.setSuccess(false);

			return new ResponseEntity<CommonApiResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.setResponseMessage("Congralutions, Course Purchased!!!");
		response.setSuccess(true);

		return new ResponseEntity<CommonApiResponse>(response, HttpStatus.OK);

	}

	public ResponseEntity<BookingResponseDto> fetchAllBookings() {

		BookingResponseDto response = new BookingResponseDto();

		List<Booking> bookings = this.bookingService.getAllBookings();

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("No Course Purchases found!!");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Fetched all Course Purchases!!!");
		response.setSuccess(true);

		return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<BookingResponseDto> fetchAllBookingsByCourse(Integer courseId) {

		BookingResponseDto response = new BookingResponseDto();

		if (courseId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);

		}

		Course course = this.courseService.getById(courseId);

		if (course == null) {
			response.setResponseMessage("course not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Booking> bookings = this.bookingService.getByCourse(course);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("No Course Purchases found!!");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Fetched all Course Purchases!!!");
		response.setSuccess(true);

		return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<BookingResponseDto> fetchAllBookingsByCustomer(Integer customerId) {

		BookingResponseDto response = new BookingResponseDto();

		if (customerId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);

		}

		User customer = this.userService.getUserById(customerId);

		if (customer == null) {
			response.setResponseMessage("customer not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Booking> bookings = this.bookingService.getBookingByCustomer(customer);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("No Course Purchases found");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Fetched Course Purchases!!");
		response.setSuccess(true);

		return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
	}

	public ResponseEntity<BookingResponseDto> fetchAllBookingsByMentorId(Integer mentorId) {

		BookingResponseDto response = new BookingResponseDto();

		if (mentorId == null) {
			response.setResponseMessage("missing input");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);

		}

		User mentor = this.userService.getUserById(mentorId);

		if (mentor == null) {
			response.setResponseMessage("mentor not found!!!");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.BAD_REQUEST);
		}

		List<Booking> bookings = this.bookingService.getByMentor(mentor);

		if (CollectionUtils.isEmpty(bookings)) {
			response.setResponseMessage("No Course Purchases found");
			response.setSuccess(false);

			return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
		}

		response.setBookings(bookings);
		response.setResponseMessage("Fetched all Course Purchases!!");
		response.setSuccess(true);

		return new ResponseEntity<BookingResponseDto>(response, HttpStatus.OK);
	}

}
