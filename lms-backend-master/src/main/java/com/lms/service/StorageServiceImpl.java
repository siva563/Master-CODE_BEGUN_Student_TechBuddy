package com.lms.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class StorageServiceImpl implements StorageService {

	@Value("${com.lms.profile.image.folder.path}")
	private String PROFILE_PIC_BASEPATH;

	@Value("${com.lms.course.video.folder.path}")
	private String COURSE_VIDEO_BASEPATH;

	@Value("${com.lms.course.notes.folder.path}")
	private String COURSE_NOTE_BASEPATH;

	@Override
	public List<String> loadAll() {
		File dirPath = new File(PROFILE_PIC_BASEPATH);
		return Arrays.asList(dirPath.list());
	}

	@Override
	public String store(MultipartFile file) {

		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
		File filePath = new File(PROFILE_PIC_BASEPATH, fileName);
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			FileCopyUtils.copy(file.getInputStream(), out);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Resource load(String fileName) {
		File filePath = new File(PROFILE_PIC_BASEPATH, fileName);
		if (filePath.exists())
			return new FileSystemResource(filePath);
		return null;
	}

	@Override
	public void delete(String fileName) {
		File filePath = new File(PROFILE_PIC_BASEPATH, fileName);
		if (filePath.exists())
			filePath.delete();
	}

	@Override
	public List<String> loadAllCourseVideo() {
		File dirPath = new File(COURSE_VIDEO_BASEPATH);
		return Arrays.asList(dirPath.list());
	}

	@Override
	public String storeCourseVideo(MultipartFile file) {

		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
		File filePath = new File(COURSE_VIDEO_BASEPATH, fileName);
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			FileCopyUtils.copy(file.getInputStream(), out);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Resource loadCourseVideo(String fileName) {
		File filePath = new File(COURSE_VIDEO_BASEPATH, fileName);
		if (filePath.exists())
			return new FileSystemResource(filePath);
		return null;
	}

	@Override
	public void deleteCourseVideo(String fileName) {
		File filePath = new File(COURSE_VIDEO_BASEPATH, fileName);
		if (filePath.exists())
			filePath.delete();
	}

	@Override
	public List<String> loadAllCourseNote() {
		File dirPath = new File(COURSE_NOTE_BASEPATH);
		return Arrays.asList(dirPath.list());
	}

	@Override
	public String storeCourseNote(MultipartFile file) {

		String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ext;
		File filePath = new File(COURSE_NOTE_BASEPATH, fileName);
		try (FileOutputStream out = new FileOutputStream(filePath)) {
			FileCopyUtils.copy(file.getInputStream(), out);
			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Resource loadCourseNote(String fileName) {
		File filePath = new File(COURSE_NOTE_BASEPATH, fileName);
		if (filePath.exists())
			return new FileSystemResource(filePath);
		return null;
	}

	@Override
	public void deleteCourseNote(String fileName) {
		File filePath = new File(COURSE_NOTE_BASEPATH, fileName);
		if (filePath.exists())
			filePath.delete();
	}

}
