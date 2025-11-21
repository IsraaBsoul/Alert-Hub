package com.example.Evaluation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Evaluation.dto.TopDeveloperResponse;
import com.example.Evaluation.service.LabelAggregationService;
import com.example.Evaluation.service.TopDeveloperService;
import com.example.Evaluation.service.TotalTasksService;

@RestController
@RequestMapping("/evaluation/developer")
public class EvaluationController {
	@Autowired
    private TopDeveloperService topDeveloperService;

    @Autowired
    private LabelAggregationService labelAggregationService;

    @Autowired
    private TotalTasksService totalTasksService;
    
    @GetMapping("/most")
    public TopDeveloperResponse getTopDeveloperByLabel(
            @RequestParam String label,
            @RequestParam int since) {

        return topDeveloperService.getTopDeveloperByLabel(label, since);
    }
    
    
    
    
    @GetMapping("/{developerId}/label-aggregate")
    public Map<String, Integer> getLabelAggregation(
            @PathVariable String developerId,  // no need to repeat the name
            @RequestParam int since) {

        return labelAggregationService.getLabelAggregationForDeveloper(developerId, since);
    }
    
    
    
    
    @GetMapping("/{developerId}/task-amount")
    public int getTotalTasks(
            @PathVariable String developerId,
            @RequestParam int since) {

        return totalTasksService.getTotalTasksForDeveloper(developerId, since);
    }
}
