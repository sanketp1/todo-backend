package com.app.todo.controller;

import com.app.todo.entity.Todo;
import com.app.todo.exceptions.InvalidTodoException;
import com.app.todo.exceptions.TodoNotFoundException;
import com.app.todo.payload.AddTodoRequest;
import com.app.todo.payload.UpdateTodo;
import com.app.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing to-do items.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/todos")
@Slf4j
public class TodoController {

    private final TodoService todoService;

    /**
     * Adds a new to-do item to the system.
     *
     * @param todoRequest an instance of {@link AddTodoRequest} containing the details needed to create a new to-do item.
     *                    The `todoRequest` should include:
     *                    - `title` (String): The title of the to-do item. This is required.
     *                    - `description` (String): A brief description of the to-do item. This is optional.
     *                    - `dueDate` (LocalDateTime): The date and time by which the to-do item should be completed. This is optional.
     *                    - `priority` (Priority): The priority level of the to-do item (e.g., LOW, MEDIUM, HIGH). This is optional.
     * @return a {@link ResponseEntity} containing the newly created {@link Todo} and HTTP status code 201 (Created) if successful.
     * @throws InvalidTodoException if the provided request is invalid (e.g., due date is in the past).
     */
    @PostMapping
    public ResponseEntity<Todo> addTodo(
            @RequestBody AddTodoRequest todoRequest) throws InvalidTodoException {
        log.info("Adding new to-do item: {}", todoRequest);
        Todo todo = todoService.addTodo(todoRequest);
        log.info("To-do item created successfully: {}", todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    /**
     * Retrieves a to-do item by its unique identifier.
     *
     * @param id the unique identifier of the to-do item to be retrieved.
     * @return a {@link ResponseEntity} containing the {@link Todo} if found, with HTTP status code 200 (OK).
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(
            @PathVariable long id) throws TodoNotFoundException {
        log.info("Retrieving to-do item with ID: {}", id);
        Todo todo = todoService.getTodoById(id);
        log.info("To-do item retrieved successfully: {}", todo);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    /**
     * Retrieves all to-do items in the system.
     *
     * @return a {@link ResponseEntity} containing a list of all {@link Todo} items and HTTP status code 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        log.info("Retrieving all to-do items");
        List<Todo> todos = todoService.getAllTodos();
        log.info("Retrieved {} to-do items", todos.size());
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    /**
     * Updates an existing to-do item.
     *
     * @param id the unique identifier of the to-do item to be updated.
     * @param updateTodo an instance of {@link UpdateTodo} containing the updated details of the to-do item.
     *                    The `updateTodo` should include:
     *                    - `title` (String): The updated title of the to-do item. This is optional.
     *                    - `description` (String): The updated description of the to-do item. This is optional.
     *                    - `dueDate` (LocalDateTime): The updated date and time by which the to-do item should be completed. This is optional.
     *                    - `priority` (Priority): The updated priority level of the to-do item. This is optional.
     * @return a {@link ResponseEntity} containing the updated {@link Todo} and HTTP status code 200 (OK) if successful.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable long id,
            @RequestBody UpdateTodo updateTodo)  throws TodoNotFoundException {
        log.info("Updating to-do item with ID: {}", id);
        Todo updatedTodo = todoService.updateTodo(id, updateTodo);
        log.info("To-do item updated successfully: {}", updatedTodo);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    /**
     * Marks a to-do item as completed.
     *
     * @param id the unique identifier of the to-do item to be marked as completed.
     * @return a {@link ResponseEntity} with HTTP status code 204 (No Content) if successful.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    @PatchMapping("/{id}/markOrUnmark")
    public ResponseEntity<Void> markAsCompleted(
            @PathVariable long id)  throws TodoNotFoundException {
        log.info("Marking or Unmarking to-do item as completed with ID: {}", id);
        todoService.markOrUnmarkedAsCompleted(id);
        log.info("To-do item marked or Unmarked as completed with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes a to-do item by its unique identifier.
     *
     * @param id the unique identifier of the to-do item to be deleted.
     * @return a {@link ResponseEntity} with HTTP status code 204 (No Content) if successful.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable long id)  throws TodoNotFoundException {
        log.info("Deleting to-do item with ID: {}", id);
        todoService.deleteById(id);
        log.info("To-do item deleted successfully with ID: {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Deletes all to-do items.
     *
     * @return a {@link ResponseEntity} with HTTP status code 204 (No Content).
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        log.info("Deleting all to-do items");
        todoService.deleteAll();
        log.info("All to-do items deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
