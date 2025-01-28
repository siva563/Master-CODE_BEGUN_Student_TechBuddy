package com.lms.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

	public static String getProperDateTimeFormatFromEpochTime(String epochTimeString) {

		long epochTimeMillis = Long.parseLong(epochTimeString);

		Instant instant = Instant.ofEpochMilli(epochTimeMillis);
		ZoneId zoneId = ZoneId.systemDefault();

		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);

		// Define the desired date format
		String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";

		// Format LocalDateTime to a specific date format
		String formattedDateTime = formatLocalDateTime(localDateTime, dateFormatPattern);

		// Print the formatted date and time
		System.out.println("Formatted DateTime: " + formattedDateTime);

		return formattedDateTime;
	}

	private static String formatLocalDateTime(LocalDateTime dateTime, String dateFormatPattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
		return dateTime.format(formatter);
	}
	
	public static LocalDateTime getTokenExpirationDate() {
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")); // You can change the time zone here
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS z");
        String formattedDateTime = now.format(formatter);
        return now.toLocalDateTime();
	}

}
