package com.example.Metric.Controller;

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

import com.example.Metric.dto.MetricDto;
import com.example.Metric.model.Metric;
import com.example.Metric.service.MetricService;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

	@Autowired
    private MetricService metricService;
	
	@PostMapping
    public Metric createMetric(@RequestBody Metric metric) {
        return metricService.createMetric(metric);
    }
	
//	@GetMapping
//    public List<Metric> getAllMetrics() {
//        return metricService.getAllMetrics();
//    }
//
//    @GetMapping("/{id}")
//    public Metric getMetricById(@PathVariable UUID id) {
//        return metricService.getMetricById(id);
//    }
    
    @GetMapping
    public List<MetricDto> getAllMetrics() {
        List<Metric> metrics = metricService.getAllMetrics();
        return metricService.toDtoList(metrics);
    }

    @GetMapping("/{id}")
    public MetricDto getMetricById(@PathVariable UUID id) {
        Metric metric = metricService.getMetricById(id);
        return metricService.toDto(metric);
    }


    @PutMapping("/{id}")
    public Metric updateMetric(@PathVariable UUID id, @RequestBody Metric metric) {
        return metricService.updateMetric(id, metric);
    }

    @DeleteMapping("/{id}")
    public String deleteMetric(@PathVariable UUID id) {
        metricService.deleteMetric(id);
        return "Metric deleted.";
    }
}
