package com.lms.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dao.StudentDao;
import com.lms.dto.AddressDto;
import com.lms.dto.EducationDto;
import com.lms.dto.StudentDto;
import com.lms.entity.Student;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentRepository;

    public StudentService(StudentDao studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // public List<Student> getAllStudents() {
    // return studentRepository.findAll();
    // }

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private StudentDto mapToDTO(Student student) {
        // Conversion logic
        StudentDto dto = new StudentDto();
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
            addressDTO.setDistrict(dto.getAddress().getDistrict());
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