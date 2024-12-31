package com.lms.service;

import com.lms.entity.MentorDetail;

public interface MentorDetailService {

	MentorDetail addMentorDetail(MentorDetail detail);
	
	MentorDetail updateMentorDetail(MentorDetail detail);

	MentorDetail getById(int detailId);

}
