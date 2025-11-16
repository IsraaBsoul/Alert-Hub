package com.example.Metric.model;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "metrics")
public class Metric {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Integer userId;

    private String name;
    
    @Enumerated(EnumType.STRING)
    private LabelType label;

    private Integer threshold;

    private Integer timeFrameHours;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

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

	public LabelType getLabel() {
		return label;
	}

	public void setLabel(LabelType label) {
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
