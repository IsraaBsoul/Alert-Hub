package com.example.Action.repository;
import com.example.Action.model.Action;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActionRepository extends JpaRepository<Action, UUID> {
	List<Action> findByIsEnabledTrueAndIsDeletedFalse();

	 boolean existsByIdAndIsEnabledTrueAndIsDeletedFalse(UUID id);
	 
	 Action getActionById(UUID id);

}

