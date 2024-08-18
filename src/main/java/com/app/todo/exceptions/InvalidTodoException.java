package com.app.todo.exceptions;

public class InvalidTodoException extends  Exception{
    public InvalidTodoException(String message) {
        super(message);
    }
}
