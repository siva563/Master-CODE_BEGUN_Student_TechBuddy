package com.lms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dao.BatchDao;
import com.lms.entity.Batch;

@Service
public class BatchService {

	@Autowired
	private BatchDao batchRepository;

	public Batch createBatch(Batch batch) {
		return batchRepository.save(batch);
	}

	public List<Batch> getAllBatches() {
		return batchRepository.findAll();
	}

	public Batch updateBatch(Long id, Batch updatedBatch) {
		return batchRepository.findById(id).map(batch -> {
			batch.setBatchNo(updatedBatch.getBatchNo());
			batch.setBatchName(updatedBatch.getBatchName());
			batch.setStartTime(updatedBatch.getStartTime());
			batch.setEndTime(updatedBatch.getEndTime());
			batch.setSlots(updatedBatch.getSlots());
			batch.setNoOfDays(updatedBatch.getNoOfDays());
			batch.setBatchType(updatedBatch.getBatchType());
			batch.setCourse(updatedBatch.getCourse());
			batch.setCreatedBy(updatedBatch.getCreatedBy());
			return batchRepository.save(batch);
		}).orElseThrow(() -> new RuntimeException("Batch not found with id: " + id));
	}

	public void deleteBatch(Long id) {
		if (batchRepository.existsById(id)) {
			batchRepository.deleteById(id);
		} else {
			throw new RuntimeException("Batch not found with id: " + id);
		}
	}
}
