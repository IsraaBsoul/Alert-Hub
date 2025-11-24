package com.example.Processor.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Processor.dto.TaskDto;

@FeignClient(name = "loader-service", url = "http://localhost:8080/api/loader")
public interface LoaderClient {

    @GetMapping("/tasks")
    List<TaskDto> getAllTasks();

    
}
