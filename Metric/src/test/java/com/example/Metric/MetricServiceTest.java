package com.example.Metric;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.Metric.model.LabelType;
import com.example.Metric.model.Metric;
import com.example.Metric.repository.MetricRepository;
import com.example.Metric.service.MetricService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MetricServiceTest {

	@Mock
    private MetricRepository metricRepository;

    @InjectMocks
    private MetricService metricService;

    private Metric sampleMetric;
    private UUID sampleId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleId = UUID.randomUUID();
        sampleMetric = new Metric();
        sampleMetric.setId(sampleId);
        sampleMetric.setUserId(1001);
        sampleMetric.setName("bug_10_12");
        sampleMetric.setLabel(LabelType.BUG);
        sampleMetric.setThreshold(10);
        sampleMetric.setTimeFrameHours(12);
    }

    @Test
    void testCreateMetric() {
        when(metricRepository.save(sampleMetric)).thenReturn(sampleMetric);

        Metric created = metricService.createMetric(sampleMetric);

        assertNotNull(created);
        assertEquals(sampleMetric.getId(), created.getId());
        verify(metricRepository, times(1)).save(sampleMetric);
    }

    @Test
    void testGetAllMetrics() {
        when(metricRepository.findAll()).thenReturn(Arrays.asList(sampleMetric));

        List<Metric> metrics = metricService.getAllMetrics();

        assertEquals(1, metrics.size());
        assertEquals(sampleMetric.getName(), metrics.get(0).getName());
        verify(metricRepository, times(1)).findAll();
    }

    @Test
    void testGetMetricById_Found() {
        when(metricRepository.findById(sampleId)).thenReturn(Optional.of(sampleMetric));

        Metric result = metricService.getMetricById(sampleId);

        assertNotNull(result);
        assertEquals(sampleId, result.getId());
        verify(metricRepository, times(1)).findById(sampleId);
    }

    @Test
    void testGetMetricById_NotFound() {
        UUID unknownId = UUID.randomUUID();
        when(metricRepository.findById(unknownId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> metricService.getMetricById(unknownId));

        assertEquals("Metric not found", exception.getMessage());
        verify(metricRepository, times(1)).findById(unknownId);
    }

    @Test
    void testUpdateMetric() {
        Metric updatedMetric = new Metric();
        updatedMetric.setUserId(2002);
        updatedMetric.setName("enhanced_metric");
        updatedMetric.setLabel(LabelType.HELP_WANTED);
        updatedMetric.setThreshold(5);
        updatedMetric.setTimeFrameHours(6);

        when(metricRepository.findById(sampleId)).thenReturn(Optional.of(sampleMetric));
        when(metricRepository.save(any(Metric.class))).thenAnswer(i -> i.getArguments()[0]);

        Metric result = metricService.updateMetric(sampleId, updatedMetric);

        assertEquals(2002, result.getUserId());
        assertEquals("enhanced_metric", result.getName());
        assertEquals(LabelType.HELP_WANTED, result.getLabel());
        assertEquals(5, result.getThreshold());
        assertEquals(6, result.getTimeFrameHours());

        verify(metricRepository, times(1)).findById(sampleId);
        verify(metricRepository, times(1)).save(sampleMetric);
    }

    @Test
    void testDeleteMetric() {
        doNothing().when(metricRepository).deleteById(sampleId);

        metricService.deleteMetric(sampleId);

        verify(metricRepository, times(1)).deleteById(sampleId);
    }
}
