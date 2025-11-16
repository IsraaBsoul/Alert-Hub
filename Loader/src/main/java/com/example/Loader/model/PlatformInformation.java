package com.example.Loader.model;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "platform_information")
public class PlatformInformation {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    private LocalDateTime timestamp;
    private String owner_id;
    private String project;
    private String tag;
    private String label;
    private String developer_id;
    private String task_number;
    private String environment;
    private String user_story;
    private String task_point;
    private String sprint;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String fileName;
    
    
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDeveloper_id() {
		return developer_id;
	}
	public void setDeveloper_id(String developer_id) {
		this.developer_id = developer_id;
	}
	public String getTask_number() {
		return task_number;
	}
	public void setTask_number(String task_number) {
		this.task_number = task_number;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getUser_story() {
		return user_story;
	}
	public void setUser_story(String user_story) {
		this.user_story = user_story;
	}
	public String getTask_point() {
		return task_point;
	}
	public void setTask_point(String task_point) {
		this.task_point = task_point;
	}
	public String getSprint() {
		return sprint;
	}
	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

    
}
