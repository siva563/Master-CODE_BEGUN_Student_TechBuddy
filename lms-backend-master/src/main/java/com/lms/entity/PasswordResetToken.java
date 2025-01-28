package com.lms.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime expirationDate;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

}
