package com.lms.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "batches", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Batch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "batch_number", nullable = false, unique = true)
	private String batchNumber; // Format: CB-0001

	@Column(name = "batch_name", nullable = false, unique = true)
	private String batchName;

	@Column(name = "start_date", nullable = false)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	private LocalDate endDate;

	@Column(name = "batch_timings", nullable = false)
	private LocalTime batchTimings; // 24-hour format

	@Column(name = "batch_status", nullable = false)
	private String batchStatus; // "Active" until end date

	@Column(name = "batch_type", nullable = false)
	private String batchType; // "Online" or "Offline"

	@Column(name = "course_type", nullable = false)
	private String courseType; // Dropdown

	@Column(name = "frontend_trainer_name")
	private String frontendTrainerName;

	@Column(name = "backend_trainer_name")
	private String backendTrainerName;

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setName(String batchName) {
		this.batchName = batchName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getBatchTimings() {
		return batchTimings;
	}

	public void setBatchTimings(LocalTime batchTimings) {
		this.batchTimings = batchTimings;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getBatchType() {
		return batchType;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getFrontendTrainerName() {
		return frontendTrainerName;
	}

	public void setFrontendTrainerName(String frontendTrainerName) {
		this.frontendTrainerName = frontendTrainerName;
	}

	public String getBackendTrainerName() {
		return backendTrainerName;
	}

	public void setBackendTrainerName(String backendTrainerName) {
		this.backendTrainerName = backendTrainerName;
	}
}
