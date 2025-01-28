package com.lms.service;

public interface EmailService {
	public void sendPasswordResetEmail(String to, String resetLink);
}
