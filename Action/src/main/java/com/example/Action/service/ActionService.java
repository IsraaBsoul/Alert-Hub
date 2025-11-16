package com.example.Action.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Action.dto.JobDto;
import com.example.Action.model.Action;
import com.example.Action.repository.ActionRepository;

@Service
public class ActionService {
	
	@Autowired
    private ActionRepository actionRepository;
	
	
	public List<Action> getAllActions() {
        return actionRepository.findAll();
    }

    public Action saveAction(Action action) {
    	
        return actionRepository.save(action);
    }
    
    public Action updateAction(UUID id, Action updatedAction) {
        Action existing = actionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Action not found"));
        
        existing.setName(updatedAction.getName());
        existing.setOwnerId(updatedAction.getOwnerId());
        existing.setActionType(updatedAction.getActionType());
        existing.setRunOnTime(updatedAction.getRunOnTime());
        existing.setRunOnDay(updatedAction.getRunOnDay());
        existing.setMessage(updatedAction.getMessage());
        existing.setToInfo(updatedAction.getToInfo());
        existing.setIsEnabled(updatedAction.getIsEnabled());
        existing.setIsDeleted(updatedAction.getIsDeleted());
        existing.setConditions(updatedAction.getConditions());
        existing.setLastUpdate(LocalDateTime.now());
        
        updatedAction.setLastUpdate(LocalDateTime.now());

        return actionRepository.save(existing);
    }

    public void deleteAction(UUID id) {
        actionRepository.deleteById(id);
    }
    
 
    public JobDto toJobDto(Action action) {
        JobDto job = new JobDto();
        job.setActionId(action.getId());
        job.setOwnerId(action.getOwnerId());
        job.setName(action.getName());
        job.setMessage(action.getMessage());
        job.setToInfo(action.getToInfo());
        job.setActionType(action.getActionType() != null ? action.getActionType().name() : null);
        job.setRunOnDay(action.getRunOnDay() != null ? action.getRunOnDay().name() : null);
        job.setRunOnTime(action.getRunOnTime() != null ? action.getRunOnTime().toString() : null);
        job.setScheduledAt(LocalDateTime.now());
        job.setConditions(action.getConditions());
        return job;
    }
    
 
    public List<Action> getEnabledActions() {
        return actionRepository.findByIsEnabledTrueAndIsDeletedFalse();
    }
    
    public boolean isActionValid(UUID id) {
        return actionRepository.existsByIdAndIsEnabledTrueAndIsDeletedFalse(id);
    }
    
    public Action getActionById(UUID id){
    	return actionRepository.getActionById(id);
    	   
    }





}
