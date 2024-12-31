package com.lms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

@Service
public class GitHubService {

	@Value("${github.token}")
	private String githubToken;

	@Value("${github.owner}")
	private String githubOwner;

	@Value("${github.repo}")
	private String githubRepo;

	private final RestTemplate restTemplate = new RestTemplate();

	public String triggerWorkflow(String workflowFileName, String branch, Map<String, Object> inputs) {
		String url = String.format("https://api.github.com/repos/%s/%s/actions/workflows/%s/dispatches", githubOwner,
				githubRepo, workflowFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + githubToken);
		headers.set("Content-Type", "application/json");
		Map<String, Object> body = new HashMap<>();
		body.put("ref", branch);
		body.put("inputs", inputs);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

		return response.getBody();

	}

	public String getWorkflowRuns(String workflowFileName) {
		String url = String.format("https://api.github.com/repos/%s/%s/actions/workflows/%s/runs", githubOwner,
				githubRepo, workflowFileName);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + githubToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		return response.getBody();
	}

	public List<String> getWorkflowSummary(String workflowFileName) {
		String url = String.format("https://api.github.com/repos/%s/%s/actions/workflows/%s/runs", githubOwner,
				githubRepo, workflowFileName);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + githubToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<String> result = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());

			JsonNode runs = root.path("workflow_runs");
			for (JsonNode run : runs) {
				String name = run.path("name").asText();
				String status = run.path("status").asText();
				String conclusion = run.path("conclusion").asText();
				result.add(String.format("Workflow: %s | Status: %s | Conclusion: %s", name, status, conclusion));
			}
		} catch (Exception e) {
			result.add("Error processing response: " + e.getMessage());
		}

		return result;
	}

	// Create a Batch folder in Github Repo - when new Batch is started

	public String createBatchFolder(String batchName) {
		String url = String.format("https://api.github.com/repos/%s/%s/contents/%s/.gitkeep", githubOwner, githubRepo,
				batchName);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + githubToken);
		headers.set("Content-Type", "application/json");

		Map<String, String> body = new HashMap<>();
		body.put("message", "Create folder for " + batchName);
		body.put("content", Base64.getEncoder().encodeToString("".getBytes())); // Empty file to create folder

		HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

		return response.getBody();
	}

	// Create Student Folder in Spiecfic Batch, when New Student is added into our
	// Platfrom

	public String addStudentFolder(String batchName, String studentName) {
		String url = String.format("https://api.github.com/repos/%s/%s/contents/%s/%s/.gitkeep", githubOwner,
				githubRepo, batchName, studentName);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + githubToken);
		headers.set("Content-Type", "application/json");

		Map<String, String> body = new HashMap<>();
		body.put("message", String.format("Create folder for %s in %s", studentName, batchName));
		body.put("content", Base64.getEncoder().encodeToString("".getBytes())); // Empty file to create folder

		HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

		return response.getBody();
	}
	
	// add the assignment to students

	public String addAssignment(String batchName, String studentName, String assignmentName, String fileContent) {
		String url = String.format("https://api.github.com/repos/%s/%s/contents/%s/%s/%s/code.html", githubOwner,
				githubRepo, batchName, studentName, assignmentName);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + githubToken);
		headers.set("Content-Type", "application/json");

		Map<String, String> body = new HashMap<>();
		body.put("message", String.format("Add %s for %s in %s", assignmentName, studentName, batchName));
		body.put("content", Base64.getEncoder().encodeToString(fileContent.getBytes()));

		HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

		return response.getBody();
	}
	
//	public String addAssignmentFiles(AssignmentPayload payload) {
//	    String batchName = payload.getBatchName();
//	    String studentName = payload.getStudentName();
//	    String assignmentName = payload.getAssignmentName();
//
//	    // Base folder path
//	    String basePath = String.format("%s/%s/%s", batchName, studentName, assignmentName);
//
//	    // Iterate over all files in the payload
//	    for (FilePayload file : payload.getFiles()) {
//	        String filePath = String.format("%s/%s", basePath, file.getFileName());
//	        addFileToGitHub(filePath, file.getContent(), file.getCommitMessage());
//	    }
//	    return "Assignment files added successfully.";
//	}

	// Helper method to add a single file
//	private void addFileToGitHub(String filePath, String content, String commitMessage) {
//	    String url = String.format(
//	        "https://api.github.com/repos/%s/%s/contents/%s",
//	        githubOwner, githubRepo, filePath
//	    );
//
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.set("Authorization", "Bearer " + githubToken);
//	    headers.set("Content-Type", "application/json");
//
//	    Map<String, String> body = new HashMap<>();
//	    body.put("message", commitMessage);
//	    body.put("content", Base64.getEncoder().encodeToString(content.getBytes()));
//
//	    HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
//
//	    RestTemplate restTemplate = new RestTemplate();
//	    restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
//	}


}
