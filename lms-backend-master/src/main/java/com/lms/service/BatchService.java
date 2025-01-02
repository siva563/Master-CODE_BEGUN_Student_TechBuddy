package com.lms.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dao.BatchDao;
import com.lms.entity.Batch;

@Service
public class BatchService {

	@Autowired
	private BatchDao batchRepository;

	public Batch createBatch(Batch batch) {
		// Check if batch name is unique
		if (batchRepository.existsByBatchName(batch.getBatchName())) {
			throw new IllegalArgumentException("Batch name must be unique!");
		}

		// Auto-generate Batch Number (CB-000X format)
		String batchNumber = generateBatchNumber();
		batch.setBatchNumber(batchNumber);

		// Set Batch Status to "Active" by default
		batch.setBatchStatus("Active");

		return batchRepository.save(batch);
	}

	public List<Batch> getAllBatches() {
		return batchRepository.findAll();
	}

	public Batch updateBatch(Long id, Batch updatedBatch) {
		Batch existingBatch = batchRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Batch not found!"));

		// Update fields
		if (!existingBatch.getBatchName().equals(updatedBatch.getBatchName())
				&& batchRepository.existsByBatchName(updatedBatch.getBatchName())) {
			throw new IllegalArgumentException("Batch name must be unique!");
		}

		existingBatch.setName(updatedBatch.getBatchName());
		existingBatch.setStartDate(updatedBatch.getStartDate());
		existingBatch.setEndDate(updatedBatch.getEndDate());
		existingBatch.setBatchTimings(updatedBatch.getBatchTimings());
		existingBatch.setCourseType(updatedBatch.getCourseType());
		existingBatch.setFrontendTrainerName(updatedBatch.getFrontendTrainerName());
		existingBatch.setBackendTrainerName(updatedBatch.getBackendTrainerName());
		existingBatch.setBatchType(updatedBatch.getBatchType());

		// Automatically set status based on end date
		existingBatch.setBatchStatus(updatedBatch.getEndDate().isAfter(LocalDate.now()) ? "Active" : "Completed");

		return batchRepository.save(existingBatch);
	}

	public void deleteBatch(Long id) {
		if (!batchRepository.existsById(id)) {
			throw new IllegalArgumentException("Batch not found!");
		}
		batchRepository.deleteById(id);
	}

	public Optional<Batch> getBatchById(Long id) {
		return batchRepository.findById(id);
	}

	private String generateBatchNumber() {
		long count = batchRepository.count();
		return String.format("CB-%04d", count + 1);
	}

}
