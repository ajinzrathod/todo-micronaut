package com.incubyte;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/todos")
public class TodosController {
    @Inject
    private TodoService todoService;

    public TodosController(TodoService todosService) {
        this.todoService = todosService;
    }

    @Post
    public MutableHttpResponse<Todo> save(@Body Todo todo) {
        if (todo.getTitle() == null || todo.getTitle().isEmpty()) {
            return HttpResponse.badRequest().body(todo);
        }
        todoService.save(todo);
        return HttpResponse.ok().body(todo);
    }

    @Get("/open")
    public List<Todo> open() {
        return todoService.getTodos(Status.OPEN);
    }

    @Post("/{id}")
    public Todo markAsDone(String id) {
        return todoService.markAsDone(id);
    }
}
