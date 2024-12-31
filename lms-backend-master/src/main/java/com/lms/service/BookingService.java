package com.lms.service;

import java.util.List;

import com.lms.entity.Booking;
import com.lms.entity.Course;
import com.lms.entity.User;

public interface BookingService {

	Booking addBooking(Booking booking);

	Booking updateBooking(Booking booking);

	Booking getById(int bookingId);

	Booking findByBookingId(String bookingId);

	List<Booking> getBookingByCustomer(User customer);

	List<Booking> getByMentor(User mentor);

	List<Booking> getByCourse(Course course);

	List<Booking> getAllBookings();

	List<Booking> getByCourseAndCustomer(Course course, User customer);

}
