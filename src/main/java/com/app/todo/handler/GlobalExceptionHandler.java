package com.app.todo.handler;

import com.app.todo.exceptions.InvalidTodoException;
import com.app.todo.exceptions.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidTodoException.class)
    public ResponseEntity<Map<String,Object>> handleInvalidTodoException(InvalidTodoException e){
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(_responseGenerator(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleTodoNotFoundException(TodoNotFoundException e){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(_responseGenerator(e.getMessage(),HttpStatus.NOT_FOUND.value()));
    }

    private Map<String,Object> _responseGenerator(String message, int statusCode){
        Map<String, Object> map = new HashMap<>();
            map.put("statusCode", statusCode);
        map.put("message", message);
        return map;
    }

}
