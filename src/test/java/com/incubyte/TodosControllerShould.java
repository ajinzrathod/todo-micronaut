package com.incubyte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TodosControllerShould {

    private Todo todo;

    @Mock
    private TodoService todosService;

    @BeforeEach
    public void init(){
        todo = new Todo("Remember ajinkya", Status.OPEN);
    }

    @Test
    public void call_the_todo_service_to_save_todo() {
        // Arrange
        TodosController todosController = new TodosController(todosService);

        // Act
        todosController.save(todo);

        // Assert
        Todo todoResponse = verify(todosService).save(todo);
    }

    @Test
    public void invoke_todo_service_to_get_open_todos() {
        // Arrange
        TodosController todosController = new TodosController(todosService);

        // Act
        todosController.open();


        // Assert
        List<Todo> savedTodo = verify(todosService).getTodos(Status.OPEN);
    }

    @Test
    public void mark_as_done() {
        // Arrange
        TodosController todosController = new TodosController(todosService);

        // Act
        todosController.markAsDone("1");


        // Assert
        Todo savedTodo = verify(todosService).markAsDone("1");
    }
}