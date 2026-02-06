package com.example.cp11.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cp11.DTO.Requests.CreateTaskRequest;
import com.example.cp11.DTO.Requests.UpdateTaskRequest;
import com.example.cp11.DTO.Responses.TaskResponse;
import com.example.cp11.Entities.Task;
import com.example.cp11.Repositories.TaskRepository;


@Service
public class TaskService {
    @Autowired
    private TaskRepository tr; 
    @Transactional
    public TaskResponse createTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());

        task.setCompleted(false);
        
        Task savedTask = tr.save(task);
        return convertToResponse(savedTask);
    }
    
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks;
        tasks = tr.findAll();
        return tasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = tr.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        return convertToResponse(task);
    }
    
    @Transactional
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
        Task task = tr.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        if (request.getCompleted() != null) {
            task.setCompleted(request.getCompleted());
        }
        
        Task updatedTask = tr.save(task);
        return convertToResponse(updatedTask);
    }
    
    @Transactional
    public void deleteTask(Long id) {
        if (!tr.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        tr.deleteById(id);
    }
    
    private TaskResponse convertToResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.isCompleted()
        );
    }
    
}
