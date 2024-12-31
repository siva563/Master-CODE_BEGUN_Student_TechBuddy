package com.lms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "batches")
public class Batch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String batchNo;

	@Column(nullable = false)
	private String batchName;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Column(nullable = false)
	private LocalDateTime endTime;

	@Column(nullable = false)
	private int slots;

	@Column(nullable = false)
	private int noOfDays;

	@Column(nullable = false)
	private String createdBy;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdTime = LocalDateTime.now();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BatchType batchType;

	@Column(nullable = false)
	private String course;
}

enum BatchType {
	ONLINE, OFFLINE
}
