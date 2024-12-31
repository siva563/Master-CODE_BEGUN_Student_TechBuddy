package com.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lms.dao.OneCompilerUserDao;
import com.lms.entity.OneCompilerUser;

@Service
public class OneCompilerChallengeService {

    @Autowired
    private OneCompilerUserDao userRepository;

    @Value("${onecompiler.api.key}")
    private String apiKey;

    public String generateChallengeUrl(String email) {
        // Fetch the OneCompiler user details
        OneCompilerUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("OneCompiler user not found for email: " + email));

        // Construct the challenge URL
        System.out.println("user.getApiToken() " + user.getApiToken());
        String challengeUrl = String.format(
                "https://onecompiler.com/embed/challenges/434gyhdju/test-1?apiKey=%s&userApiToken=%s",
                apiKey, user.getApiToken());

        return challengeUrl;
    }
}
