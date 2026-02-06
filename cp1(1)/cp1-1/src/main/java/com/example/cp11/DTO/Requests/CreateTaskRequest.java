package com.example.cp11.DTO.Requests;
import lombok.Data;

@Data
public class CreateTaskRequest {//DTO запроса на создание задачи
    private String title;
    private boolean completed;
}
