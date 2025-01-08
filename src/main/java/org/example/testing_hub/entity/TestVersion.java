package org.example.testing_hub.entity;

import jakarta.persistence.*;

@Entity
public class TestVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int versionNumber;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    // Getters and Setters
}
