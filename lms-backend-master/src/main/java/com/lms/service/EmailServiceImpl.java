package com.lms.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lms.dto.StudentDto;
import com.lms.entity.Student;

@Service
public class EmailServiceImpl implements EmailService {
	private final JavaMailSender mailSender;
	private StudentDto studentRepository;
	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendPasswordResetEmail(String to, String resetLink) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Password Reset Request");
		message.setText("To reset your password, click the following link: " + resetLink);
		mailSender.send(message);
	}
	/*
	 * public void sendPasswordResetEmail(String email) { Student student =
	 * studentRepository.findByEmail(email); if (student == null) { throw new
	 * RuntimeException("Student not found"); }
	 * 
	 * // Create a unique token String token = UUID.randomUUID().toString();
	 * PasswordResetToken resetToken = new PasswordResetToken();
	 * resetToken.setToken(token);
	 * resetToken.setExpirationDate(LocalDateTime.now().plusHours(1)); // Token
	 * expires in 1 hour resetToken.setStudent(student);
	 * 
	 * tokenRepository.save(resetToken);
	 * 
	 * // Send reset link email String resetLink =
	 * "http://localhost:8080/reset-password?token=" + token;
	 * sendPasswordResetEmail(student.getEmail(), resetLink); }
	 */
}

