
package com.example.Processor.client;

import com.example.Processor.dto.MetricDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Metric", url = "http://localhost:8081/api/metrics")
public interface MetricsClient {

    @GetMapping("/{id}")
    MetricDto getMetricById(@PathVariable("id") Integer id);

    @GetMapping
    List<MetricDto> getMetricsByIds(@RequestParam List<Integer> ids);
}
