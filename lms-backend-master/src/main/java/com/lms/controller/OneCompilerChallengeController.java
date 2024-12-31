package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.service.OneCompilerChallengeService;

@RestController
@RequestMapping("/api/onecompiler")
public class OneCompilerChallengeController {

    @Autowired
    private OneCompilerChallengeService challengeService;

    @GetMapping("/start-challenge")
    public ResponseEntity<String> startChallenge(@RequestParam String email) {
        String challengeUrl = challengeService.generateChallengeUrl(email);
        return ResponseEntity.ok(challengeUrl);
    }
}
