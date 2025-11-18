package com.example.Logger.repository;
import com.example.Logger.model.LogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<LogEntry, Integer> {
}

