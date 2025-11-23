package com.example.Action.controller;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.example.Action.util.JwtUserContext;
import com.example.Action.util.PermissionClient;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/actions")
public class ActionController {
	
	@Autowired
	private KafkaProducerService kafkaProducerService ; 
	@Autowired
	private ActionService actionService;
	@Autowired
	private PermissionClient permissionClient;

	
	
//    @PostMapping("/publish")
//    public String publishSingle(@RequestBody Action action) {
//
//        JobDto job = actionService.toJobDto(action);
//        kafkaProducerService.publishJob(job);
//
//        return "Job published successfully!";
//    }
    
    @PostMapping("/publish")
    public ResponseEntity<?> publishSingle(
            @RequestBody Action action,
            HttpServletRequest request) {

        int requesterId = JwtUserContext.getUserId(request);

        boolean allowed =
                permissionClient.hasPermission(requesterId, "triggerProcess")
                || permissionClient.hasPermission(requesterId, "admin");

        if (!allowed) {
            return ResponseEntity.status(403)
                    .body("Forbidden - You lack permission triggerProcess/Admin");
        }

        JobDto job = actionService.toJobDto(action);
        kafkaProducerService.publishJob(job);

        return ResponseEntity.ok("Job published successfully!");
    }

	
	
	@GetMapping
    public List<Action> getAll() {
        return actionService.getAllActions();
    }

//    @PostMapping
//    public Action create(@RequestBody Action action) {
//        return actionService.saveAction(action);
//    }
//    
	
	@PostMapping
	public ResponseEntity<?> create(
	        @RequestBody Action action,
	        HttpServletRequest request) {

	    int requesterId = JwtUserContext.getUserId(request);

	    boolean allowed =
	            permissionClient.hasPermission(requesterId, "createAction") ||
	            permissionClient.hasPermission(requesterId, "admin");

	    if (!allowed) {
	        return ResponseEntity.status(403)
	                .body("Forbidden - You do not have permission to create actions");
	    }

	    return ResponseEntity.ok(actionService.saveAction(action));
	}

//    @PutMapping("/{id}")
//    public Action update(@PathVariable UUID id, @RequestBody Action action) {
//        return actionService.updateAction(id, action);
//    }
	
	@PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Action action,
            HttpServletRequest request) {

        int requesterId = JwtUserContext.getUserId(request);

        boolean allowed =
                permissionClient.hasPermission(requesterId, "updateAction")
                || permissionClient.hasPermission(requesterId, "admin");

        if (!allowed) {
            return ResponseEntity.status(403)
                    .body("Forbidden - You lack permission updateAction/Admin");
        }

        return ResponseEntity.ok(actionService.updateAction(id, action));
    }
    
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable UUID id) {
//        actionService.deleteAction(id);
//    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {

        int requesterId = JwtUserContext.getUserId(request);

        boolean allowed =
                permissionClient.hasPermission(requesterId, "deleteAction")
                || permissionClient.hasPermission(requesterId, "admin");

        if (!allowed) {
            return ResponseEntity.status(403)
                    .body("Forbidden - You lack permission DeleteAction/Admin");
        }

        actionService.deleteAction(id);
        return ResponseEntity.ok("Action deleted successfully");
    }

}
