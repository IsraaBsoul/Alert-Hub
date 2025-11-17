package com.example.Metric.dto;

import java.util.UUID;

public class MetricDto {
	
	private UUID id;
    private String label; 
    private Integer threshold;
    private Integer timeFrameHours;
    private Integer userId;
    private String name;
    
    
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}
	
	
	public void setId(UUID id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public Integer getTimeFrameHours() {
		return timeFrameHours;
	}
	public void setTimeFrameHours(Integer timeFrameHours) {
		this.timeFrameHours = timeFrameHours;
	}
    
    

}
