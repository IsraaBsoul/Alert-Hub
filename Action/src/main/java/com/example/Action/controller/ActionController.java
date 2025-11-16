package com.example.Action.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Action.dto.JobDto;
import com.example.Action.model.Action;
import com.example.Action.service.ActionService;
import com.example.Action.service.KafkaProducerService;

@RestController
@RequestMapping("/api/actions")
public class ActionController {
	
	@Autowired
	private KafkaProducerService kafkaProducerService ; 
	@Autowired
	private ActionService actionService;
	
	
    @PostMapping("/publish")
    public String publishSingle(@RequestBody Action action) {

        JobDto job = actionService.toJobDto(action);
        kafkaProducerService.publishJob(job);

        return "Job published successfully!";
    }

   
    @PostMapping("/publish/all")
    public String publishAll() {

        int count = kafkaProducerService.publishAll();
        return count + " jobs published successfully!";
    }

	
	
	@GetMapping
    public List<Action> getAll() {
        return actionService.getAllActions();
    }

    @PostMapping
    public Action create(@RequestBody Action action) {
        return actionService.saveAction(action);
    }
    
    @PutMapping("/{id}")
    public Action update(@PathVariable UUID id, @RequestBody Action action) {
        return actionService.updateAction(id, action);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        actionService.deleteAction(id);
    }

}
