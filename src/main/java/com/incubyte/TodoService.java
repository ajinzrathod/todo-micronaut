package com.incubyte;

import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public List<Todo> getTodos(Status status) {
        return todoRepository.findByStatusOrderById(status);
    }

    public Todo markAsDone(String id) {
        Todo todo= todoRepository.findById(Long.valueOf(id)).get();
        todo.setStatus(Status.CLOSED);
        return todoRepository.update(todo);

    }
}
