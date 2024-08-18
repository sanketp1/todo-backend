package com.app.todo.payload;

import com.app.todo.entity.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTodoRequest {

    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Priority priority;

}
