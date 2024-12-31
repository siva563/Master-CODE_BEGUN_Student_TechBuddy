package com.lms.controller;

import com.lms.entity.OneCompilerUser;
import com.lms.service.OneCompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/onecompiler")
public class OneCompilerController {

    @Autowired
    private OneCompilerService oneCompilerService;

    @PostMapping("/create-user")
    public ResponseEntity<OneCompilerUser> createUser(@RequestParam String name, @RequestParam String email) {
        OneCompilerUser user = oneCompilerService.createUserOnOneCompiler(name, email);
        return ResponseEntity.ok(user);
    }
}
