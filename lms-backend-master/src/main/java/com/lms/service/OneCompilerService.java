package com.lms.service;

import com.lms.dao.OneCompilerUserDao;
import com.lms.entity.OneCompilerUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OneCompilerService {

    @Autowired
    private OneCompilerUserDao userRepository;

    public OneCompilerUser createUserOnOneCompiler(String name, String email) {
        String oneCompilerApiUrl = "https://onecompiler.com/api/v1/createUser?access_token=codebegun8d6wbju4edc7anacjf7opsv823lvw4zbjbpjw3xns8g3awbq15zczbx0cxalizdh0rp5lmnzlek2gj389um632snlrufzfr5yydpq1i9dioc539golhm99ks"; // Example API URL

        RestTemplate restTemplate = new RestTemplate();

        // Request payload
        Map<String, String> requestPayload = new HashMap<>();
        requestPayload.put("name", name);
        requestPayload.put("email", email);

        // Call OneCompiler API
        Map<String, Object> response = restTemplate.postForObject(oneCompilerApiUrl, requestPayload, Map.class);

        // Extract data from response
        String oneCompilerId = response.get("_id").toString();
        String picture = response.get("picture").toString();
        String thumbnail = response.get("thumbnail").toString();
        Map<String, String> apiData = (Map<String, String>) response.get("api");
        String apiToken = apiData.get("token");

        // Save data to the database
        OneCompilerUser user = new OneCompilerUser();
        user.setName(name);
        user.setEmail(email);
        user.setOneCompilerUserId(oneCompilerId);
        user.setPicture(picture);
        user.setThumbnail(thumbnail);
        user.setApiToken(apiToken);

        return userRepository.save(user);
    }
}
