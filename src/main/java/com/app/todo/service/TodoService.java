package com.app.todo.service;

import com.app.todo.entity.Todo;
import com.app.todo.exceptions.InvalidTodoException;
import com.app.todo.exceptions.TodoNotFoundException;
import com.app.todo.payload.AddTodoRequest;
import com.app.todo.payload.UpdateTodo;

import java.util.List;

/**
 * Service interface for managing to-do items.
 */
public interface TodoService {

    /**
     * Adds a new to-do item to the system.
     *
     * @param todoRequest an instance of {@link AddTodoRequest} containing the details needed to create a new to-do item.
     *                    The `todoRequest` should include:
     *                    - `title` (String): The title of the to-do item. This is required.
     *                    - `description` (String): A brief description of the to-do item. This is optional.
     *                    - `dueDate` (LocalDateTime): The date and time by which the to-do item should be completed. This is optional.
     *                    - `priority` (Priority): The priority level of the to-do item (e.g., LOW, MEDIUM, HIGH). This is optional.
     * @return an instance of {@link Todo} representing the newly created to-do item, including its unique identifier and creation timestamp.
     * @throws InvalidTodoException if the provided `todoRequest` is invalid (e.g., missing required fields).
     */
    Todo addTodo(AddTodoRequest todoRequest) throws InvalidTodoException;

    /**
     * Retrieves a to-do item by its unique identifier.
     *
     * @param id the unique identifier of the to-do item to be retrieved.
     * @return an instance of {@link Todo} representing the to-do item with the specified ID, or `null` if no item is found with the provided ID.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    Todo getTodoById(long id) throws TodoNotFoundException;

    /**
     * Retrieves all to-do items in the system.
     *
     * @return a list of {@link Todo} objects representing all to-do items.
     */
    List<Todo> getAllTodos();

    /**
     * Updates an existing to-do item.
     *
     * @param id the unique identifier of the to-do item to be updated.
     * @param updateTodo an instance of {@link UpdateTodo} containing the updated details of the to-do item.
     *                   The `updateTodo` should include:
     *                   - `title` (String): The updated title of the to-do item. This is optional.
     *                   - `description` (String): The updated description of the to-do item. This is optional.
     *                   - `dueDate` (LocalDateTime): The updated date and time by which the to-do item should be completed. This is optional.
     *                   - `priority` (Priority): The updated priority level of the to-do item. This is optional.
     * @return an instance of {@link Todo} representing the updated to-do item.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    Todo updateTodo(long id, UpdateTodo updateTodo) throws TodoNotFoundException;

    /**
     * Marks a to-do item as completed.
     *
     * @param id the unique identifier of the to-do item to be marked or unmarked as completed.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    void markOrUnmarkedAsCompleted(long id) throws TodoNotFoundException;

    /**
     * Deletes a to-do item by its unique identifier.
     *
     * @param id the unique identifier of the to-do item to be deleted.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    void deleteById(long id) throws TodoNotFoundException;

    /**
     * Deletes all to-do items.
     */
    void deleteAll();
}
