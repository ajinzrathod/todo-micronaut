package com.incubyte;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MicronautTest
public class TodosControllerTest {

  @Inject
  @Client("/")
  HttpClient client;

  Map<String, String> todo1;
  Map<String, String> todo2;

  @Inject
  @Client("/")
  HttpClient httpClient;

  @BeforeEach
  public void init() {

    todo1 = new HashMap();
    todo1.put("title", "Remember ajinkya");
    todo1.put("status", "OPEN");

    todo2 = new HashMap();
    todo2.put("title", "Remember eggs");
    todo2.put("status", "OPEN");
  }

  @Test
  public void should_save_todos() {
    // ACT
    Map<String, String> response = client.toBlocking()
                    .retrieve(HttpRequest.POST("/todos", todo1),
                            Argument.mapOf(String.class, String.class));

    // Assert
    Assertions.assertThat(response.get("id")).isNotNull();
    Assertions.assertThat(response.get("id")).isNotEmpty();
    Assertions.assertThat(response.get("title")).isEqualTo("Remember ajinkya");


    Map<String, String> response2 =
            client
                    .toBlocking()
                    .retrieve(HttpRequest.POST("/todos", todo2), Argument.mapOf(String.class, String.class));

    // Assert
    Assertions.assertThat(response2.get("id")).isNotNull();
    Assertions.assertThat(response2.get("id")).isNotEmpty();
    Assertions.assertThat(response2.get("title")).isEqualTo("Remember eggs");
    Assertions.assertThat(response2.get("status")).isEqualTo("OPEN");


    List<Map> todos = httpClient
            .toBlocking()
            .retrieve(HttpRequest.GET("/todos/open"), Argument.listOf(Map.class));
    Assertions.assertThat(todos.size()).isEqualTo(2);
  }

  @Test
  public void should_not_save_todos_if_todo_is_empty() {
    // Arrange
    Map todo = new HashMap();
    todo.put("title", "");
    todo.put("status", "OPEN");

    // ACT
    // Assertions.shouldHaveThrown(HttpClientResponseException.class);
    org.junit.jupiter.api.Assertions.assertThrows(
            HttpClientResponseException.class,
            () -> {
              HttpResponse response = client
                      .toBlocking()
                      .retrieve(HttpRequest.POST("/todos", todo),
                              Argument.of(HttpResponse.class), Argument.of(Todo.class));
            }
    );
  }

  @Test
  public void mark_todo_as_done() {
    Map todo = new HashMap();

    todo.put("title", "Mark me as done");
    todo.put("status", "OPEN");
    Map<String, String> response = client.toBlocking()
            .retrieve(HttpRequest.POST("/todos", todo),
                    Argument.mapOf(String.class, String.class));


    Map todos = httpClient
            .toBlocking()
            .retrieve(HttpRequest.POST("/todos/1", todo),(Map.class));
    Assertions.assertThat(todos.get("status")).isEqualTo("CLOSED");
    // Assert
  }
}
