package com.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dao.CourseSectionTopicDao;
import com.lms.entity.CourseSectionTopic;

@Service
public class CourseSectionTopicServiceImpl implements CourseSectionTopicService {

	@Autowired
	private CourseSectionTopicDao courseSectionTopicDao;

	@Override
	public CourseSectionTopic add(CourseSectionTopic topic) {
		// TODO Auto-generated method stub
		return courseSectionTopicDao.save(topic);
	}

}
