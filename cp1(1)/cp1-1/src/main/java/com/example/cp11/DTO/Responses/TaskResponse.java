package com.example.cp11.DTO.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskResponse {//DTO ответа
    private Long id;
    private String title;
    private boolean completed;
}
