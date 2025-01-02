package com.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.entity.Batch;
import com.lms.service.BatchService;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

	@Autowired
	private BatchService batchService;

	@PostMapping("/createBatch")
	public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
		// Validate batch type
		if (!batch.getBatchType().equalsIgnoreCase("Online") &&
				!batch.getBatchType().equalsIgnoreCase("Offline")) {
			throw new IllegalArgumentException("Invalid batch type! Must be 'Online' or 'Offline'.");
		}

		Batch createdBatch = batchService.createBatch(batch);
		return ResponseEntity.ok(createdBatch);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Batch> updateBatch(@PathVariable Long id, @RequestBody Batch batch) {
		// Validate batch type
		if (!batch.getBatchType().equalsIgnoreCase("Online") &&
				!batch.getBatchType().equalsIgnoreCase("Offline")) {
			throw new IllegalArgumentException("Invalid batch type! Must be 'Online' or 'Offline'.");
		}

		Batch updatedBatch = batchService.updateBatch(id, batch);
		return ResponseEntity.ok(updatedBatch);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBatch(@PathVariable Long id) {
		batchService.deleteBatch(id);
		return ResponseEntity.ok("Batch deleted successfully!");
	}

	@GetMapping("/{id}")
	public ResponseEntity<Batch> getBatch(@PathVariable Long id) {
		Batch batch = batchService.getBatchById(id)
				.orElseThrow(() -> new IllegalArgumentException("Batch not found!"));
		return ResponseEntity.ok(batch);
	}

	@GetMapping("/getAllBatches")
	public ResponseEntity<List<Batch>> getAllBatches() {
		List<Batch> batches = batchService.getAllBatches();
		return ResponseEntity.ok(batches);
	}
}
