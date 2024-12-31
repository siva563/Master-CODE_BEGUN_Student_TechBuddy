package com.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dao.BookingDao;
import com.lms.entity.Booking;
import com.lms.entity.Course;
import com.lms.entity.User;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDao bookingDao;

	@Override
	public Booking addBooking(Booking booking) {
		// TODO Auto-generated method stub
		return bookingDao.save(booking);
	}

	@Override
	public Booking updateBooking(Booking booking) {
		// TODO Auto-generated method stub
		return bookingDao.save(booking);
	}

	@Override
	public Booking getById(int bookingId) {
		Optional<Booking> optionalBooking = bookingDao.findById(bookingId);

		if (optionalBooking.isPresent()) {
			return optionalBooking.get();
		} else {
			return null;
		}

	}

	@Override
	public Booking findByBookingId(String bookingId) {
		// TODO Auto-generated method stub
		return this.bookingDao.findByBookingId(bookingId);
	}

	@Override
	public List<Booking> getBookingByCustomer(User customer) {
		// TODO Auto-generated method stub
		return bookingDao.findByCustomerOrderByIdDesc(customer);
	}

	@Override
	public List<Booking> getByMentor(User mentor) {
		// TODO Auto-generated method stub
		return bookingDao.findAllBookingsByMentorOrderByIdDesc(mentor);
	}

	@Override
	public List<Booking> getByCourse(Course course) {
		// TODO Auto-generated method stub
		return bookingDao.findByCourseOrderByIdDesc(course);
	}

	@Override
	public List<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return bookingDao.findAll();
	}

	@Override
	public List<Booking> getByCourseAndCustomer(Course course, User customer) {
		// TODO Auto-generated method stub
		return bookingDao.findByCourseAndCustomer(course, customer);
	}

}
