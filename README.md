
# Todo App Backend

This is a simple Todo application built using Spring Boot. It allows users to manage their tasks effectively by adding, updating, marking as completed, and deleting tasks.

## Frontend

The frontend for this application is built using Flutter. You can find the frontend repository [here](https://github.com/sanketp1/todo_app.git).

## Features

- **Add a Todo**: Create a new task with a title, description, due date, and priority level.
- **View Todos**: Retrieve a specific task by its ID or view all tasks.
- **Update a Todo**: Modify an existing task's details.
- **Mark/Unmark as Completed**: Mark a task as completed or unmark it.
- **Delete a Todo**: Remove a task by its ID or delete all tasks.

## Entity Structure

The `Todo` entity represents a task in the system:

```java
@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 255)
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
```

### Priority Enum
The `Priority` enum represents the priority levels for a task:
- `LOW`
- `MEDIUM`
- `HIGH`

## REST API Endpoints

### Add a Todo
- **Endpoint**: `POST /api/v1/todos`
- **Request Body**:
  ```json
  {
    "title": "Buy groceries",
    "description": "Milk, Eggs, Bread",
    "dueDate": "2024-08-25T18:30:00",
    "priority": "HIGH"
  }
  ```
- **Response**: 201 Created

### Get Todo by ID
- **Endpoint**: `GET /api/v1/todos/{id}`
- **Response**: 200 OK

### Get All Todos
- **Endpoint**: `GET /api/v1/todos`
- **Response**: 200 OK

### Update a Todo
- **Endpoint**: `PUT /api/v1/todos/{id}`
- **Request Body**:
  ```json
  {
    "title": "Buy groceries",
    "description": "Milk, Eggs, Bread, Butter",
    "dueDate": "2024-08-26T18:30:00",
    "priority": "MEDIUM"
  }
  ```
- **Response**: 200 OK

### Mark/Unmark as Completed
- **Endpoint**: `PATCH /api/v1/todos/{id}/markOrUnmark`
- **Response**: 204 No Content

### Delete Todo by ID
- **Endpoint**: `DELETE /api/v1/todos/{id}`
- **Response**: 204 No Content

### Delete All Todos
- **Endpoint**: `DELETE /api/v1/todos`
- **Response**: 204 No Content

## Logging
The application uses the `@Slf4j` annotation for logging key actions such as creating, retrieving, updating, and deleting tasks. This helps track the application's behavior and troubleshoot issues.

## Exception Handling
The application includes a `GlobalExceptionHandler` class that manages exceptions like `InvalidTodoException` and `TodoNotFoundException`. It returns appropriate HTTP status codes and messages to the client.

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/todo-app.git
   ```
2. Navigate to the project directory:
   ```bash
   cd todo-app
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Technologies Used

- **Spring Boot** - Framework for building the application
- **JPA/Hibernate** - ORM for database interaction
- **Lombok** - Simplifies Java code with annotations like `@Data` and `@Slf4j`
- **Maven** - Dependency management and build tool

## Future Improvements

- **User Authentication**: Secure the API endpoints with user authentication.
- **Pagination**: Implement pagination for the list of tasks.
- **Search and Filter**: Add features to search and filter tasks based on criteria.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
