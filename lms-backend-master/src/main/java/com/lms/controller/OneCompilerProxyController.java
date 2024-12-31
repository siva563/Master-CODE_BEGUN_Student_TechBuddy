package com.lms.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lms.service.OneCompilerChallengeService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/onecompiler")
public class OneCompilerProxyController {

    @Autowired
    private OneCompilerChallengeService challengeService;

    @GetMapping("/proxy-challenge")
    public void proxyChallenge(HttpServletResponse response, @RequestParam String email) throws IOException {
        // Generate the challenge URL
        String challengeUrl = challengeService.generateChallengeUrl(email);

        // Set CORS headers (optional, for additional flexibility)
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");

        // Redirect to the OneCompiler challenge URL
        response.sendRedirect(challengeUrl);
    }
}
