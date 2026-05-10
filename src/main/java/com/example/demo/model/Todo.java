package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.UUID;

public class Todo {
    private final String id;
    private final Instant createdAt;

    @NotBlank
    private String title;
    private boolean completed;

    public Todo(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.completed = false;
        this.createdAt = Instant.now();
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public Instant getCreatedAt() { return createdAt; }
}
