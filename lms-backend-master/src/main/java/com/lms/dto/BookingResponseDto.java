package com.lms.dto;

import java.util.ArrayList;
import java.util.List;

import com.lms.entity.Booking;

import lombok.Data;

@Data
public class BookingResponseDto extends CommonApiResponse {

	private List<Booking> bookings = new ArrayList();

}
