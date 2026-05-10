package com.example.demo.service;

import com.example.demo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TodoService {

    private final Map<String, Todo> store = new ConcurrentHashMap<>();

    public Collection<Todo> findAll() {
        return store.values();
    }

    public Optional<Todo> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Todo create(String title) {
        Todo todo = new Todo(title);
        store.put(todo.getId(), todo);
        return todo;
    }

    public Optional<Todo> update(String id, String title, Boolean completed) {
        return findById(id).map(todo -> {
            if (title != null) todo.setTitle(title);
            if (completed != null) todo.setCompleted(completed);
            return todo;
        });
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }
}
