package com.app.todo.repository;

import com.app.todo.entity.Priority;
import com.app.todo.entity.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Todo t SET t.completed = CASE WHEN t.completed = true THEN false ELSE true END WHERE t.id = :id")
    void updateByIdAndCompleted(long id);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE Todo t SET t.title = :title, t.description = :description , t.dueDate = :dueDate, t.priority = :priority WHERE t.id = :id"
    )
    void updateTodoBy(long id, String title, String description, LocalDateTime dueDate, Priority priority);
}