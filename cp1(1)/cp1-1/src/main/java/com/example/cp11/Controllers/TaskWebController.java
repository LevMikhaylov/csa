package com.example.cp11.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.cp11.DTO.Requests.CreateTaskRequest;
import com.example.cp11.DTO.Requests.UpdateTaskRequest;
import com.example.cp11.DTO.Responses.TaskResponse;
import com.example.cp11.Services.TaskService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskWebController {
     @Autowired
    private TaskService taskService;
    
    // Главная страница - список всех задач
    @GetMapping
    public String homePage(Model model) {
        List<TaskResponse> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "index";
    }
    
    // Форма создания задачи
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("taskRequest", new CreateTaskRequest());
        return "create";
    }
    
    // Создание задачи
    @PostMapping("/create")
    public String createTask(@ModelAttribute CreateTaskRequest request, Model model) {
        try {
            TaskResponse response = taskService.createTask(request);
            return "redirect:/tasks/view/" + response.getId();
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("taskRequest", request);
            return "create";
        }
    }
    
    // Просмотр одной задачи
    @GetMapping("/view/{id}")
    public String viewTask(@PathVariable Long id, Model model) {
        try {
            TaskResponse task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            return "view";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Задача не найдена: " + e.getMessage());
            return "error";
        }
    }
    
    // Форма редактирования задачи
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            TaskResponse task = taskService.getTaskById(id);
            UpdateTaskRequest request = new UpdateTaskRequest();
            request.setCompleted(task.isCompleted());
            
            model.addAttribute("task", task);
            model.addAttribute("updateRequest", request);
            return "edit";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Задача не найдена: " + e.getMessage());
            return "error";
        }
    }
    
    // Обновление задачи
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id,
                            @ModelAttribute UpdateTaskRequest request,
                            Model model) {
        try {
            TaskResponse updatedTask = taskService.updateTask(id, request);
            return "redirect:/tasks/view/" + id;
        } catch (RuntimeException e) {
            TaskResponse task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("updateRequest", request);
            return "edit";
        }
    }
    
    // Подтверждение удаления
    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        try {
            TaskResponse task = taskService.getTaskById(id);
            model.addAttribute("task", task);
            return "delete";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Задача не найдена: " + e.getMessage());
            return "error";
        }
    }
    
    // Удаление задачи
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
