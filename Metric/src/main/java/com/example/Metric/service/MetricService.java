package com.example.Metric.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Metric.dto.MetricDto;
import com.example.Metric.model.Metric;
import com.example.Metric.repository.MetricRepository;

@Service
public class MetricService {
	
	@Autowired
    private MetricRepository metricRepository;
	
	public Metric createMetric(Metric metric) {
        return metricRepository.save(metric);
    }

    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }
    
    public Metric getMetricById(UUID id) {
        return metricRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Metric not found"));
    }

    public Metric updateMetric(UUID id, Metric updated) {
        Metric existing = getMetricById(id);

        existing.setUserId(updated.getUserId());
        existing.setName(updated.getName());
        existing.setLabel(updated.getLabel());
        existing.setThreshold(updated.getThreshold());
        existing.setTimeFrameHours(updated.getTimeFrameHours());

        return metricRepository.save(existing);
    }

    public void deleteMetric(UUID id) {
        metricRepository.deleteById(id);
    }
    
    public MetricDto toDto(Metric metric) {
        MetricDto dto = new MetricDto();
    
        dto.setId(metric.getId());
        dto.setUserId(metric.getUserId());
        dto.setName(metric.getName());
        dto.setLabel(metric.getLabel().name()); // convert enum to string
        dto.setThreshold(metric.getThreshold());
        dto.setTimeFrameHours(metric.getTimeFrameHours());
        
        
        return dto;
    }

    public List<MetricDto> toDtoList(List<Metric> metrics) {
        return metrics.stream()
                      .map(this::toDto)
                      .collect(Collectors.toList());
    }

}


