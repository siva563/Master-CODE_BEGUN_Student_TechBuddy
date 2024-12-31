package com.lms.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lms.service.GitHubService;

@RestController
@RequestMapping("/api/github")
public class GitHubController {
	@Autowired
	private GitHubService gitHubService;

	@PostMapping("/trigger-workflow")

	public String triggerWorkflow(@RequestBody Map<String, Object> payload) {
		String workflowFileName = (String) payload.get("workflowFileName");
		String branch = (String) payload.get("branch");

		Map<String, Object> inputs = (Map<String, Object>) payload.get("inputs");

		return gitHubService.triggerWorkflow(workflowFileName, branch, inputs);
	}

	@GetMapping("/workflow-runs/{workflowFileName}")
	public String getWorkflowRuns(@PathVariable("workflowFileName") String workflowFileName) {
		return gitHubService.getWorkflowRuns(workflowFileName);
	}

	@GetMapping("/workflow-summary/{workflowFileName}")
	public List<String> getWorkflowSummary(@PathVariable("workflowFileName") String workflowFileName) {
		return gitHubService.getWorkflowSummary(workflowFileName);
	}

	@PostMapping("/create-batch-folder")
	public String createBatchFolder(@RequestParam String batchName) {
		return gitHubService.createBatchFolder(batchName);
	}

	@PostMapping("/add-student-folder")
	public String addStudentFolder(@RequestParam String batchName, @RequestParam String studentName) {
		return gitHubService.addStudentFolder(batchName, studentName);
	}

	@PostMapping("/add-assignment")
	public String addAssignment(@RequestParam String batchName, @RequestParam String studentName,
			@RequestParam String assignmentName, @RequestParam String fileContent) {
		return gitHubService.addAssignment(batchName, studentName, assignmentName, fileContent);
	}
}
