package com.lms.service;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.lms.dao.AddressDao;
import com.lms.dao.PasswordResetTokenDao;
import com.lms.dao.StudentDao;
import com.lms.dao.UserDao;
import com.lms.dto.AddressDto;
import com.lms.dto.CommonApiResponse;
import com.lms.dto.EducationDto;
import com.lms.dto.StudentDto;
import com.lms.entity.Address;
import com.lms.entity.PasswordResetToken;
import com.lms.entity.Student;
import com.lms.entity.User;
import com.lms.exception.UserSaveFailedException;
import com.lms.utility.DateTimeUtils;
import com.lms.utility.Helper;
import com.lms.utility.JwtUtils;
import com.lms.utility.Constants.ActiveStatus;
import com.lms.utility.Constants.UserRole;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.transaction.Transactional;

@Service
public class StudentService {

	@Autowired
	private StudentDao studentRepository;
	@Autowired
	private UserDao userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AddressDao addressRepository;
	
	@Autowired
	private PasswordResetTokenDao pResetTokenRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public StudentService(StudentDao studentRepository) {
		this.studentRepository = studentRepository;
	}

	public Student createStudent(Student student) {
		Student res = new Student();
		if (!userRepository.existsByEmailId(student.getEmail())) {
			
			res = studentRepository.save(student);

			User user = new User();
			user.setFirstName(student.getFirstName());
			user.setLastName(student.getLastName());
			user.setEmailId(student.getEmail());
			System.out.println(new BigDecimal(student.getTotalFee())+" "+student.getTotalFee());
			user.setAmount(new BigDecimal(student.getTotalFee()));
			user.setPhoneNo(student.getMobileNumber());
			user.setRole(UserRole.ROLE_STUDENT.value());
			user.setStatus(ActiveStatus.ACTIVE.value());
			user.setMentorDetail(null);
			user.setAddress(addressRepository.getById(Math.toIntExact(student.getAddress().getId())));

			JwtUtils jwtUtils = new JwtUtils();
			String resetToken = jwtUtils.generateToken(student.getEmail());
			String url = UriComponentsBuilder.fromHttpUrl("http://localhost:3000/reset")
	                .queryParam("token", resetToken)
	                .toUriString();
			
			userRepository.save(user);
			PasswordResetToken passwordResetToken = new PasswordResetToken();
			passwordResetToken.setToken(resetToken);
			passwordResetToken.setStudent(student);
			passwordResetToken.setExpirationDate(DateTimeUtils.getTokenExpirationDate());
			pResetTokenRepository.save(passwordResetToken);
			
			emailService.sendPasswordResetEmail(student.getEmail(), url);
		}
		return res;
	}

	public Student updateStudent(Long id, Student student) {
		Optional<Student> existingUser = studentRepository.findById(id);
		if (existingUser.isPresent()) {
			Address address = new Address();
			address.setId(existingUser.get().getAddress().getId());
			address.setBlock(student.getAddress().getBlock());
			address.setDistrict(student.getAddress().getDistrict());
			address.setPincode(student.getAddress().getPincode());
			address.setState(student.getAddress().getState());
			address.setStreet(student.getAddress().getStreet());
			address.setVillage(student.getAddress().getVillage());
			
			student.setAddress(address);
			return studentRepository.save(student);
		}
		return null;
	}

	public CommonApiResponse updateStudentPassword(String token, String password) {
		try {
			JwtUtils jwtUtils = new JwtUtils();
			String email = jwtUtils.extractUsername(token);
			User user = userRepository.findByEmailId(email);
			user.setRole(UserRole.ROLE_STUDENT.value());
			user.setStatus(ActiveStatus.ACTIVE.value());
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
			CommonApiResponse res = new CommonApiResponse();
			res.setResponseMessage("Password Created Successfully!");
			res.setSuccess(true);
			return res;
		} catch (ExpiredJwtException e) {
			throw new UserSaveFailedException("Token expired.");
		}catch (MalformedJwtException e) {
			throw new UserSaveFailedException("Invalid Token.");
		}catch (Exception e) {
			throw new UserSaveFailedException("Failed to reset password.");
		}
	}

	@Transactional
	public void deleteStudent(Long id) {
		Optional<Student> existingUser = studentRepository.findById(id);

		if (existingUser.isPresent()) {
			pResetTokenRepository.deleteByStudentId(id);
			addressRepository.deleteById(Math.toIntExact(existingUser.get().getAddress().getId()));
			int userId = userRepository.findByEmailId(existingUser.get().getEmail()).getId();
			System.out.println(userId);
			userRepository.deleteById(userId);

			studentRepository.deleteById(id);

		} else {
			throw new RuntimeException("User not found with id: " + id);
		}
	}
	// public List<Student> getAllStudents() {
	// return studentRepository.findAll();
	// }

	public List<StudentDto> getAllStudents() {
		List<Student> students = studentRepository.findAll();
		return students.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	private StudentDto mapToDTO(Student student) {
		// Conversion logic
		StudentDto dto = new StudentDto();
		dto.setId(student.getId());
		dto.setFirstName(student.getFirstName());
		dto.setLastName(student.getLastName());
		dto.setEmail(student.getEmail());
		dto.setMobileNumber(student.getMobileNumber());
		dto.setAlternativeMobileNumber(student.getAlternativeMobileNumber());
		dto.setGender(student.getGender());
		dto.setDateOfBirth(student.getDateOfBirth());
		dto.setBatch(student.getBatch());
		dto.setCourseName(student.getCourseName());
		dto.setStatus(student.getStatus());
		dto.setCourseDuration(student.getCourseDuration());
		dto.setCourseFee(student.getCourseFee());
		dto.setTotalFee(student.getTotalFee());
		dto.setAmountPaid(student.getAmountPaid());
		dto.setBalanceFee(student.getBalanceFee());

		// Convert resume and profilePicture from byte[] to Base64 String
		if (student.getResume() != null) {
			dto.setResume(Base64.getEncoder().encodeToString(student.getResume()));
		}

		if (student.getProfilePicture() != null) {
			dto.setProfilePicture(Base64.getEncoder().encodeToString(student.getProfilePicture()));
		}

		// Map social links
		if (student.getSocialLinks() != null) {
			dto.setSocialLinks(student.getSocialLinks());
		}
		AddressDto addressDTO = new AddressDto();
		// Map Address (handle null case)
		if (student.getAddress() != null) {

			addressDTO.setState(student.getAddress().getState());
			addressDTO.setDistrict(student.getAddress().getDistrict());
			addressDTO.setBlock(student.getAddress().getBlock());
			addressDTO.setVillage(student.getAddress().getVillage());
			addressDTO.setStreet(student.getAddress().getStreet());
			addressDTO.setPincode(student.getAddress().getPincode());
			dto.setAddress(addressDTO);
		} else {
			addressDTO.setDistrict("Unknown");
		}

		// Map Education (handle null case)
		if (student.getEducation() != null) {
			EducationDto educationDTO = new EducationDto();
			educationDTO.setHighestQualification(student.getEducation().getHighestQualification());
			educationDTO.setBranch(student.getEducation().getBranch());
			educationDTO.setPercentage(student.getEducation().getPercentage());
			educationDTO.setPassedOutYear(student.getEducation().getPassedOutYear());
			educationDTO.setIntermediateGroup(student.getEducation().getIntermediateGroup());
			educationDTO.setIntermediateCollege(student.getEducation().getIntermediateCollege());
			educationDTO.setIntermediatePercentage(student.getEducation().getIntermediatePercentage());
			educationDTO.setSscPercentage(student.getEducation().getSscPercentage());
			educationDTO.setUniversity(student.getEducation().getUniversity());
			educationDTO.setCollege(student.getEducation().getCollege());
			educationDTO.setSchool(student.getEducation().getSchool());
			dto.setEducation(educationDTO);
		}

		return dto;

	}

}