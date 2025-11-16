package com.example.Action.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class JobDto {

    private UUID actionId;
    private String ownerId;
    private String name;
    private String message;
    private String toInfo;
    private String actionType; 
    private String runOnDay;
    private String runOnTime; 
    private LocalDateTime scheduledAt; 
    private List<List<Integer>> conditions;
	public UUID getActionId() {
		return actionId;
	}
	public void setActionId(UUID actionId) {
		this.actionId = actionId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getToInfo() {
		return toInfo;
	}
	public void setToInfo(String toInfo) {
		this.toInfo = toInfo;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getRunOnDay() {
		return runOnDay;
	}
	public void setRunOnDay(String runOnDay) {
		this.runOnDay = runOnDay;
	}
	public String getRunOnTime() {
		return runOnTime;
	}
	public void setRunOnTime(String runOnTime) {
		this.runOnTime = runOnTime;
	}
	public LocalDateTime getScheduledAt() {
		return scheduledAt;
	}
	public void setScheduledAt(LocalDateTime scheduledAt) {
		this.scheduledAt = scheduledAt;
	}
	public List<List<Integer>> getConditions() {
		return conditions;
	}
	public void setConditions(List<List<Integer>> conditions) {
		this.conditions = conditions;
	}
    
    

    
}
