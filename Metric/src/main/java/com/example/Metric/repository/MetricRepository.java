package com.example.Metric.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Metric.model.Metric;
import java.util.UUID;

public interface MetricRepository extends JpaRepository<Metric, UUID>{

}
