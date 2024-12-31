package com.lms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "one_compiler_users")
public class OneCompilerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String oneCompilerUserId; // _id from OneCompiler API

    private String picture; // Picture URL

    private String thumbnail; // Thumbnail URL

    @Column(nullable = false)
    private String apiToken; // Token from API response

    @Column(nullable = false)
    private LocalDateTime createdTime = LocalDateTime.now();
    
}
