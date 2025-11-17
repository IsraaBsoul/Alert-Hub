package com.example.Loader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Loader.dto.TaskDto;
import com.example.Loader.model.Provider;
import com.example.Loader.service.LoaderService;

@RestController
@RequestMapping("/api/loader")
public class LoaderController {
	
	@Autowired
	private LoaderService loaderService;
	
	@GetMapping("/tasks")
	public List<TaskDto> getAllTasks() {
	    return loaderService.getAllTasks();
	}

	
	@PostMapping("/{provider}")
	public ResponseEntity<String> loadProvider(@PathVariable Provider provider) {
		String result = loaderService.loadLatestData(provider);
		return ResponseEntity.ok(result);

	}
	
	@PostMapping("/all")
	public ResponseEntity<String> loadAllProviders() {
		List<Provider> providers = List.of(Provider.gitHub, Provider.jira, Provider.clickUp);
		StringBuilder sb = new StringBuilder();
		for (Provider provider : providers) {
		    sb.append(loaderService.loadLatestData(provider)).append("\n");
		}
		return ResponseEntity.ok(sb.toString());


	}




}
