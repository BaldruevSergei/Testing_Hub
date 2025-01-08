package org.example.testing_hub.entity;

import jakarta.persistence.*;

@Entity
public class TestStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int questionGroupsCount;
    private int questionsCount;
    private int messagesCount;
    private int scalesCount;

    private int testVersionCount;
    private int resultsCount;

    // Getters and Setters
}
