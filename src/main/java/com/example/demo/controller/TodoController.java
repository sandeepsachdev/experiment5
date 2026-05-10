package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public Collection<Todo> list() {
        return todoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> get(@PathVariable String id) {
        return todoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@Valid @RequestBody CreateRequest req) {
        return todoService.create(req.title());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @RequestBody UpdateRequest req) {
        return todoService.update(id, req.title(), req.completed())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return todoService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    record CreateRequest(@NotBlank String title) {}
    record UpdateRequest(String title, Boolean completed) {}
}
