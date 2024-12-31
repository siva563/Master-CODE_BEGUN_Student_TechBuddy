package com.lms.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	// Profile Image
	List<String> loadAll();

	String store(MultipartFile file);

	Resource load(String fileName);

	void delete(String fileName);

	// Course Videos
	List<String> loadAllCourseVideo();

	String storeCourseVideo(MultipartFile file);

	Resource loadCourseVideo(String fileName);

	void deleteCourseVideo(String fileName);

	// Course Note
	List<String> loadAllCourseNote();

	String storeCourseNote(MultipartFile file);

	Resource loadCourseNote(String fileName);

	void deleteCourseNote(String fileName);

}