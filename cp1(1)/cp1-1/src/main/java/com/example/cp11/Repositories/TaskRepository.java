package com.example.cp11.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cp11.Entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>{
}
