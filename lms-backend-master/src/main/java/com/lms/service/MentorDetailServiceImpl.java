package com.lms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dao.MentorDetailDao;
import com.lms.entity.MentorDetail;

@Service
public class MentorDetailServiceImpl implements MentorDetailService {

	@Autowired
	private MentorDetailDao mentorDetailDao;

	@Override
	public MentorDetail addMentorDetail(MentorDetail detail) {
		// TODO Auto-generated method stub
		return mentorDetailDao.save(detail);
	}

	@Override
	public MentorDetail updateMentorDetail(MentorDetail detail) {
		// TODO Auto-generated method stub
		return mentorDetailDao.save(detail);
	}

	@Override
	public MentorDetail getById(int detailId) {

		Optional<MentorDetail> optional = this.mentorDetailDao.findById(detailId);

		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}

	}

}
