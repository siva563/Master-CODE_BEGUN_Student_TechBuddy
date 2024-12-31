package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.entity.Batch;
import com.lms.service.BatchService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

	@Autowired
	private BatchService batchService;

	@PostMapping("/create")
	public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
		Batch createdBatch = batchService.createBatch(batch);
		return ResponseEntity.ok(createdBatch);
	}

	@GetMapping("/all")
	public ResponseEntity<List<Batch>> getAllBatches() {
		List<Batch> batches = batchService.getAllBatches();
		return ResponseEntity.ok(batches);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Batch> updateBatch(@PathVariable Long id, @RequestBody Batch updatedBatch) {
		Batch batch = batchService.updateBatch(id, updatedBatch);
		return ResponseEntity.ok(batch);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBatch(@PathVariable Long id) {
		batchService.deleteBatch(id);
		return ResponseEntity.ok("Batch deleted successfully.");
	}
}
