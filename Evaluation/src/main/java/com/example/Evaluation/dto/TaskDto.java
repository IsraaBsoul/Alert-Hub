package com.example.Evaluation.dto;

import java.time.LocalDateTime;

public class TaskDto {
	private String ownerId;
    private String project;
    private String tag;
    private String label;
    private String developerId;
    private String taskNumber;
    private String environment;
    private String userStory;
    private String taskPoint;
    private String sprint;
    private LocalDateTime timestamp;

    // Getters and setters
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    public String getProject() { return project; }
    public void setProject(String project) { this.project = project; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getDeveloperId() { return developerId; }
    public void setDeveloperId(String developerId) { this.developerId = developerId; }

    public String getTaskNumber() { return taskNumber; }
    public void setTaskNumber(String taskNumber) { this.taskNumber = taskNumber; }

    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }

    public String getUserStory() { return userStory; }
    public void setUserStory(String userStory) { this.userStory = userStory; }

    public String getTaskPoint() { return taskPoint; }
    public void setTaskPoint(String taskPoint) { this.taskPoint = taskPoint; }

    public String getSprint() { return sprint; }
    public void setSprint(String sprint) { this.sprint = sprint; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

}
