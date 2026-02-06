package com.example.cp11.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "tasks")
@Data //Генерация геттеров и сеттеров для полей: "Идентифифкатор","Название","Статус задачи"
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "id")
    private Long id;
    @Column(nullable = false,name = "title")
    private String title;
    @Column(nullable = false,name="completed")
    private boolean completed;
}
