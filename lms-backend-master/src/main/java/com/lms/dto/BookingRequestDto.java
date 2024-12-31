package com.lms.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BookingRequestDto {

	private int courseId;

	private int customerId;

	private String cardNo;

	private String nameOnCard;

	private String cvv;

	private String expiryDate;
	
	private BigDecimal amount;

}
