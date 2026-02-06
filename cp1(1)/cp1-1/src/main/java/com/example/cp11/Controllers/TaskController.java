package com.example.cp11.Controllers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cp11.DTO.Requests.CreateTaskRequest;
import com.example.cp11.DTO.Requests.UpdateTaskRequest;
import com.example.cp11.DTO.Responses.TaskResponse;
import com.example.cp11.Services.TaskService;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {
    
    @Autowired
    private TaskService ts;
    
    @PostMapping
    @Operation(
        summary = "Create a new task",
        description = "Creates a new task with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Task created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TaskResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content
        )
    })
    public ResponseEntity<?> createTask(
            @Parameter(
                description = "Task creation request",
                required = true,
                schema = @Schema(implementation = CreateTaskRequest.class)
            )
            @RequestBody CreateTaskRequest request) {
        TaskResponse response = ts.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Get task by ID",
        description = "Retrieves a specific task by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Task found and returned",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TaskResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = @Content
        )
    })
    public ResponseEntity<TaskResponse> getTaskById(
            @Parameter(
                description = "ID of the task to retrieve",
                required = true,
                example = "123"
            )
            @PathVariable Long id) {
        TaskResponse task = ts.getTaskById(id);
        return ResponseEntity.ok(task);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Update an existing task",
        description = "Updates the details of an existing task"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Task updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = TaskResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content
        )
    })
    public ResponseEntity<TaskResponse> updateTask(
            @Parameter(
                description = "ID of the task to update",
                required = true,
                example = "123"
            )
            @PathVariable Long id, 
            
            @Parameter(
                description = "Updated task details",
                required = true,
                schema = @Schema(implementation = UpdateTaskRequest.class)
            )
            @RequestBody UpdateTaskRequest request) {
        TaskResponse updatedTask = ts.updateTask(id, request);
        return ResponseEntity.ok(updatedTask);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete a task",
        description = "Deletes a task by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Task deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Task not found",
            content = @Content
        )
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(
                description = "ID of the task to delete",
                required = true,
                example = "123"
            )
            @PathVariable Long id) {
        ts.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
    @ExceptionHandler(RuntimeException.class)
    @Operation(hidden = true) // Скрываем обработчик исключений из документации
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}