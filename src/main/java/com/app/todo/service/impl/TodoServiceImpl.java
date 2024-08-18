package com.app.todo.service.impl;

import com.app.todo.entity.Todo;
import com.app.todo.exceptions.InvalidTodoException;
import com.app.todo.exceptions.TodoNotFoundException;
import com.app.todo.payload.AddTodoRequest;
import com.app.todo.payload.UpdateTodo;
import com.app.todo.repository.TodoRepository;
import com.app.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    private static final String notFoundExcetion = "No todo found with id ";
    private static final String invalidTodoException = "Can not processed invalid Todo task which due date is before the current time";

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
    @Override
    public Todo addTodo(AddTodoRequest todoRequest) throws InvalidTodoException {

        // if due date of Todo lies in past then throwing InvalidTodoException
        if(todoRequest.getDueDate().toLocalDate().isBefore(LocalDate.now())){
            throw new InvalidTodoException(invalidTodoException);
        }

        //creating object of [Todo]
        Todo todo = new Todo();

        //setting all the field
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setDueDate(todoRequest.getDueDate());
        todo.setPriority(todoRequest.getPriority());

        //storing object
        Todo savedTodo = todoRepository.save(todo);


        return savedTodo;
    }

    /**
     * Retrieves a to-do item by its unique identifier.
     *
     * @param id the unique identifier of the to-do item to be retrieved.
     * @return an instance of {@link Todo} representing the to-do item with the specified ID, or `null` if no item is found with the provided ID.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    @Override
    public Todo getTodoById(long id) throws TodoNotFoundException {
        final Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            return todo.get();
        }
        //otherwise throwing the [TodoNotFoundException]
        throw new TodoNotFoundException(notFoundExcetion+id);
    }

    /**
     * Retrieves all to-do items in the system.
     *
     * @return a list of {@link Todo} objects representing all to-do items.
     */
    @Override
    public List<Todo> getAllTodos() {

        return todoRepository.findAll();
    }

    /**
     * Updates an existing to-do item.
     *
     * @param id         the unique identifier of the to-do item to be updated.
     * @param updateTodo an instance of {@link UpdateTodo} containing the updated details of the to-do item.
     *                   The `updateTodo` should include:
     *                   - `title` (String): The updated title of the to-do item. This is optional.
     *                   - `description` (String): The updated description of the to-do item. This is optional.
     *                   - `dueDate` (LocalDateTime): The updated date and time by which the to-do item should be completed. This is optional.
     *                   - `priority` (Priority): The updated priority level of the to-do item. This is optional.
     * @return an instance of {@link Todo} representing the updated to-do item.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    @Override
    public Todo updateTodo(long id, UpdateTodo updateTodo) throws TodoNotFoundException {

        if(!todoRepository.existsById(id)){
            throw  new TodoNotFoundException(notFoundExcetion+id);
        }

        //creating updated todo
        Todo updatedTodo = new Todo();

        updatedTodo.setId(id);
        updatedTodo.setTitle(updateTodo.getTitle());
        updatedTodo.setDescription(updateTodo.getDescription());
        updatedTodo.setDueDate(updateTodo.getDueDate());
        updatedTodo.setPriority(updateTodo.getPriority());

        //updating  [Todo] object
        todoRepository.updateTodoBy(id,updateTodo.getTitle(), updateTodo.getDescription(), updateTodo.getDueDate(), updateTodo.getPriority());

        return updatedTodo;
    }

    /**
     * Marks a to-do item as completed.
     *
     * @param id the unique identifier of the to-do item to be marked or unmarked as completed.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    @Override
    public void markOrUnmarkedAsCompleted(long id) throws TodoNotFoundException {
        //checking is [Todo] exist with given id or not
        if(!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(notFoundExcetion + id);
        }
        todoRepository.updateByIdAndCompleted(id);
    }

    /**
     * Deletes a to-do item by its unique identifier.
     *
     * @param id the unique identifier of the to-do item to be deleted.
     * @throws TodoNotFoundException if no to-do item is found with the given ID.
     */
    @Override
    public void deleteById(long id) throws TodoNotFoundException {
        //checking if Todo is exist with given id or not
        if(!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(notFoundExcetion+id);
        }

        //deleting [Todo] with given id
        todoRepository.deleteById(id);
    }

    /**
     * Deletes all to-do items.
     */
    @Override
    public void deleteAll() {
        ///deleting all [Todo]
        todoRepository.deleteAll();
    }
}
