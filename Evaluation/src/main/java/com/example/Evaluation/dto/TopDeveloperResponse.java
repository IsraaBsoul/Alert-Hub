package com.example.Evaluation.dto;

public class TopDeveloperResponse {
	private String developerId;
    private String label;
    private int taskCount;
    
	public TopDeveloperResponse(String developerId, String label, int taskCount) {
		 this.developerId = developerId;
		    this.label = label;
		    this.taskCount = taskCount;
	}
	
	public String getDeveloperId() {
		return developerId;
	}
	public void setDeveloperId(String developerId) {
		this.developerId = developerId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getTaskCount() {
		return taskCount;
	}
	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}
    
}
