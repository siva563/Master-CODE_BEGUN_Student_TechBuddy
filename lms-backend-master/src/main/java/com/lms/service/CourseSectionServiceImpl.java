package com.lms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dao.CourseSectionDao;
import com.lms.entity.Course;
import com.lms.entity.CourseSection;

@Service
public class CourseSectionServiceImpl implements CourseSectionService {

	@Autowired
	private CourseSectionDao courseSectionDao;

	@Override
	public CourseSection add(CourseSection section) {
		// TODO Auto-generated method stub
		return courseSectionDao.save(section);
	}

	@Override
	public List<CourseSection> getByCourse(Course course) {
		// TODO Auto-generated method stub
		return courseSectionDao.findByCourse(course);
	}

	@Override
	public CourseSection getById(int section) {

		Optional<CourseSection> optional = this.courseSectionDao.findById(section);

		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}

	}

}
