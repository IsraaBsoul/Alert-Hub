package com.example.Evaluation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Evaluation.dto.TopDeveloperResponse;
import com.example.Evaluation.service.LabelAggregationService;
import com.example.Evaluation.service.TopDeveloperService;
import com.example.Evaluation.service.TotalTasksService;
import com.example.Evaluation.util.JwtUserContext;
import com.example.Evaluation.util.PermissionClient;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/evaluation/developer")
public class EvaluationController {
	@Autowired
    private TopDeveloperService topDeveloperService;

    @Autowired
    private LabelAggregationService labelAggregationService;

    @Autowired
    private TotalTasksService totalTasksService;
    @Autowired
    private PermissionClient permissionClient;

    private boolean hasEvaluationPermission(int userId) {
        return permissionClient.hasPermission(userId, "triggerEvaluation")
                || permissionClient.hasPermission(userId, "admin");
    }
    
//    @GetMapping("/most")
//    public TopDeveloperResponse getTopDeveloperByLabel(
//            @RequestParam String label,
//            @RequestParam int since) {
//
//        return topDeveloperService.getTopDeveloperByLabel(label, since);
//    }
    
    @GetMapping("/most")
    public ResponseEntity<?> getTopDeveloperByLabel(
            @RequestParam String label,
            @RequestParam int since,
            HttpServletRequest request) {

        int userId = JwtUserContext.getUserId(request);
        if (!hasEvaluationPermission(userId)) {
            return ResponseEntity.status(403).body("Forbidden - You lack triggerEvaluation/Admin permission");
        }
        String requesterEmail = JwtUserContext.getUserEmail(request);

        TopDeveloperResponse response = topDeveloperService.getTopDeveloperByLabel(label, since,requesterEmail);
        return ResponseEntity.ok(response);
    }
    
    
    
    
//    @GetMapping("/{developerId}/label-aggregate")
//    public Map<String, Integer> getLabelAggregation(
//            @PathVariable String developerId,  // no need to repeat the name
//            @RequestParam int since) {
//
//        return labelAggregationService.getLabelAggregationForDeveloper(developerId, since);
//    }
    

    @GetMapping("/{developerId}/label-aggregate")
    public ResponseEntity<?> getLabelAggregation(
            @PathVariable String developerId,
            @RequestParam int since,
            HttpServletRequest request) {

        int userId = JwtUserContext.getUserId(request);
        if (!hasEvaluationPermission(userId)) {
            return ResponseEntity.status(403).body("Forbidden - You lack triggerEvaluation/Admin permission");
        }

        String requesterEmail = JwtUserContext.getUserEmail(request);
        Map<String, Integer> result = labelAggregationService.getLabelAggregationForDeveloper(developerId, since,requesterEmail);
        return ResponseEntity.ok(result);
    }
    
    
    
//    @GetMapping("/{developerId}/task-amount")
//    public int getTotalTasks(
//            @PathVariable String developerId,
//            @RequestParam int since) {
//
//        return totalTasksService.getTotalTasksForDeveloper(developerId, since);
//    }
    
    @GetMapping("/{developerId}/task-amount")
    public ResponseEntity<?> getTotalTasks(
            @PathVariable String developerId,
            @RequestParam int since,
            HttpServletRequest request) {

        int userId = JwtUserContext.getUserId(request);
        if (!hasEvaluationPermission(userId)) {
            return ResponseEntity.status(403).body("Forbidden - You lack triggerEvaluation/Admin permission");
        }
        
        String requesterEmail = JwtUserContext.getUserEmail(request);
        int totalTasks = totalTasksService.getTotalTasksForDeveloper(developerId, since,requesterEmail);
        return ResponseEntity.ok(totalTasks);
    }
}
