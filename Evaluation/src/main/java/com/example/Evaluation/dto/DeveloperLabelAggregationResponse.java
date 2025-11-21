package com.example.Evaluation.dto;

import java.util.Map;

public class DeveloperLabelAggregationResponse {
	private String developerId;
    private Map<String, Integer> labelCounts;
    
	public String getDeveloperId() {
		return developerId;
	}
	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}
	public Map<String, Integer> getLabelCounts() {
        return labelCounts;
    }
	public void setLabelCounts(Map<String, Integer> labelCounts) {
        this.labelCounts = labelCounts;
    }
}
