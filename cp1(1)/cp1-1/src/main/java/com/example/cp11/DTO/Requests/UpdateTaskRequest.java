package com.example.cp11.DTO.Requests;

import lombok.Data;

@Data
public class UpdateTaskRequest { //DTO запроса на обновление статуса
    private Boolean completed;
}
