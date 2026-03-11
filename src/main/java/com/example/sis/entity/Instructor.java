package com.example.sis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "instructors")
@Data
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String academicDegree;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}