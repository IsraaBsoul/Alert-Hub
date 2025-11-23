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
import com.example.Loader.util.JwtUserContext;
import com.example.Loader.util.PermissionClient;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/loader")
public class LoaderController {
	
	@Autowired
	private LoaderService loaderService;
    @Autowired
    private PermissionClient permissionClient;

    private boolean hasTriggerScanPermission(int userId) {
        return permissionClient.hasPermission(userId, "triggerScan")
                || permissionClient.hasPermission(userId, "admin");
    }
    
	@GetMapping("/tasks")
	public List<TaskDto> getAllTasks() {
	    return loaderService.getAllTasks();
	}

	
//	@PostMapping("/{provider}")
//	public ResponseEntity<String> loadProvider(@PathVariable Provider provider) {
//		String result = loaderService.loadLatestData(provider);
//		return ResponseEntity.ok(result);
//
//	}
	
    @PostMapping("/{provider}")
    public ResponseEntity<?> loadProvider(
            @PathVariable Provider provider,
            HttpServletRequest request) {

        int userId = JwtUserContext.getUserId(request);
        if (!hasTriggerScanPermission(userId)) {
            return ResponseEntity.status(403)
                    .body("Forbidden - You lack triggerScan/Admin permission");
        }

        String result = loaderService.loadLatestData(provider);
        return ResponseEntity.ok(result);
    }
	
//	@PostMapping("/all")
//	public ResponseEntity<String> loadAllProviders() {
//		List<Provider> providers = List.of(Provider.gitHub, Provider.jira, Provider.clickUp);
//		StringBuilder sb = new StringBuilder();
//		for (Provider provider : providers) {
//		    sb.append(loaderService.loadLatestData(provider)).append("\n");
//		}
//		return ResponseEntity.ok(sb.toString());
//	}
    
    @PostMapping("/all")
    public ResponseEntity<?> loadAllProviders(HttpServletRequest request) {

        int userId = JwtUserContext.getUserId(request);
        if (!hasTriggerScanPermission(userId)) {
            return ResponseEntity.status(403)
                    .body("Forbidden - You lack triggerScan/Admin permission");
        }

        List<Provider> providers = List.of(Provider.gitHub, Provider.jira, Provider.clickUp);
        StringBuilder sb = new StringBuilder();
        for (Provider provider : providers) {
            sb.append(loaderService.loadLatestData(provider)).append("\n");
        }
        return ResponseEntity.ok(sb.toString());
    }




}
