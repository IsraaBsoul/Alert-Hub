package com.example.Metric.Controller;

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

import com.example.Metric.dto.MetricDto;
import com.example.Metric.model.Metric;
import com.example.Metric.service.MetricService;
import com.example.Metric.util.JwtUserContext;
import com.example.Metric.util.PermissionClient;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/metrics")
public class MetricController {

	@Autowired
    private MetricService metricService;
	@Autowired
    private PermissionClient permissionClient;
	
//	@PostMapping
//    public Metric createMetric(@RequestBody Metric metric) {
//        return metricService.createMetric(metric);
//    }
    @PostMapping
    public ResponseEntity<?> createMetric(@RequestBody Metric metric, HttpServletRequest request) {
        int requesterId = JwtUserContext.getUserId(request);

        boolean allowed = permissionClient.hasPermission(requesterId, "createMetric")
                || permissionClient.hasPermission(requesterId, "admin");

        if (!allowed) {
            return ResponseEntity.status(403).body("Forbidden - You lack permission createMetric/Admin");
        }

        // Set the owner ID here in the controller
        metric.setUserId(requesterId);

        // Call the service without any changes
        Metric savedMetric = metricService.createMetric(metric);
        return ResponseEntity.ok(savedMetric);
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


//    @PutMapping("/{id}")
//    public Metric updateMetric(@PathVariable UUID id, @RequestBody Metric metric) {
//        return metricService.updateMetric(id, metric);
//    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMetric(@PathVariable UUID id, @RequestBody Metric metric, HttpServletRequest request) {
        int requesterId = JwtUserContext.getUserId(request);

        boolean allowed = permissionClient.hasPermission(requesterId, "updateMetric")
                || permissionClient.hasPermission(requesterId, "admin");

        if (!allowed) {
            return ResponseEntity.status(403).body("Forbidden - You lack permission updateMetric/Admin");
        }

        return ResponseEntity.ok(metricService.updateMetric(id, metric));
    }

//    @DeleteMapping("/{id}")
//    public String deleteMetric(@PathVariable UUID id) {
//        metricService.deleteMetric(id);
//        return "Metric deleted.";
//    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMetric(@PathVariable UUID id, HttpServletRequest request) {
        int requesterId = JwtUserContext.getUserId(request);

        boolean allowed = permissionClient.hasPermission(requesterId, "deleteMetric")
                || permissionClient.hasPermission(requesterId, "admin");

        if (!allowed) {
            return ResponseEntity.status(403).body("Forbidden - You lack permission deleteMetric/Admin");
        }

        metricService.deleteMetric(id);
        return ResponseEntity.ok("Metric deleted successfully");
    }
}
