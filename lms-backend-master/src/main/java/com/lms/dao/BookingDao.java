package com.lms.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lms.entity.Booking;
import com.lms.entity.Course;
import com.lms.entity.User;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {

	Booking findByBookingId(String bookingId);

	List<Booking> findByCustomerOrderByIdDesc(User customer);

	@Query("SELECT b FROM Booking b WHERE b.course.mentor = :mentor")
	List<Booking> findAllBookingsByMentorOrderByIdDesc(@Param("mentor") User mentor);
	
	@Query("SELECT sum(amount) FROM Booking b WHERE b.course.mentor = :mentor")
	BigDecimal findSumOfAmountFromMentorBooking(@Param("mentor") User mentor);
	
	
	@Query("SELECT count(b) FROM Booking b WHERE b.course.mentor = :mentor")
	Long findBookingCountFromMentorBooking(@Param("mentor") User mentor);

	List<Booking> findByCourseOrderByIdDesc(Course course);
	
    List<Booking> findByCourseAndCustomer(Course course, User customer);

}
