package com.example.Action.model;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Action {

	@Id
    @GeneratedValue
    private UUID id;

    private String ownerId;

    private String name;

    private LocalDate createDate;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private LocalTime runOnTime;
    
    @Enumerated(EnumType.STRING)
    @NotNull(message = "runOnDay cannot be null")
    private RunDay runOnDay;

    private String message;

    private String toInfo;

    private Boolean isEnabled;

    private Boolean isDeleted;

    private LocalDateTime lastUpdate;

    private LocalDateTime lastRun;
  
    @Convert(converter = IntegerListListConverter.class)
    private List<List<Integer>> conditions = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public LocalTime getRunOnTime() {
		return runOnTime;
	}

	public void setRunOnTime(LocalTime runOnTime) {
		this.runOnTime = runOnTime;
	}

	public RunDay getRunOnDay() {
		return runOnDay;
	}

	public void setRunOnDay(RunDay runOnDay) {
		this.runOnDay = runOnDay;
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

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public LocalDateTime getLastRun() {
		return lastRun;
	}

	public void setLastRun(LocalDateTime lastRun) {
		this.lastRun = lastRun;
	}

	public List<List<Integer>> getConditions() {
		return conditions;
	}

	public void setConditions(List<List<Integer>> conditions) {
		this.conditions = conditions;
	}

	
    
    
}
