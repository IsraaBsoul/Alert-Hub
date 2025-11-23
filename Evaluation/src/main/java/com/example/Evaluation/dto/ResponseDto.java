package com.example.Evaluation.dto;

import java.util.Map;

public class ResponseDto {

    private String mail;
    private ResponseType type;
    private String massage;

    private Long developerId;
    private String label;
    private Integer maxCount;

    private Map<String, Integer> labelCountMap;

    private Integer developerCount;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public ResponseType getType() {
		return type;
	}

	public void setType(ResponseType type) {
		this.type = type;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public Long getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(Long developerId) {
		this.developerId = developerId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public Map<String, Integer> getLabelCountMap() {
		return labelCountMap;
	}

	public void setLabelCountMap(Map<String, Integer> labelCountMap) {
		this.labelCountMap = labelCountMap;
	}

	public Integer getDeveloperCount() {
		return developerCount;
	}

	public void setDeveloperCount(Integer developerCount) {
		this.developerCount = developerCount;
	}
    
    
    

    
}
