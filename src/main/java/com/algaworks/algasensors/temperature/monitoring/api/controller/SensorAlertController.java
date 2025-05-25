package com.algaworks.algasensors.temperature.monitoring.api.controller;

import com.algaworks.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.algaworks.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sensors/{sensorId}/alert")
public class SensorAlertController {

    private final SensorAlertRepository sensorAlertRepository;

    @GetMapping
    public SensorAlertOutput search(@PathVariable TSID sensorId) {
        SensorAlert sensorAlert = getSensorAlert(sensorId);

        return convertToModel(sensorAlert);
    }

    @PutMapping
    public SensorAlertOutput createOrUpdate(@PathVariable TSID sensorId, @RequestBody SensorAlertInput input) {
        SensorAlert sensorAlert = findByIdOrDefault(sensorId);

        sensorAlert.setMinTemperature(input.getMinTemperature());
        sensorAlert.setMaxTemperature(input.getMaxTemperature());
        sensorAlertRepository.saveAndFlush(sensorAlert);

        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .minTemperature(sensorAlert.getMinTemperature())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable TSID sensorId) {
        SensorAlert sensorAlert = getSensorAlert(sensorId);
        sensorAlertRepository.delete(sensorAlert);
    }

    private SensorAlert getSensorAlert(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private SensorAlert findByIdOrDefault(TSID sensorId) {
        return sensorAlertRepository.findById(new SensorId(sensorId))
                .orElse(SensorAlert.builder()
                        .id(new SensorId(sensorId))
                        .minTemperature(null)
                        .maxTemperature(null)
                        .build());
    }

    private SensorAlertOutput convertToModel(SensorAlert sensorAlert) {
        return SensorAlertOutput.builder()
                .id(sensorAlert.getId().getValue())
                .maxTemperature(sensorAlert.getMaxTemperature())
                .minTemperature(sensorAlert.getMinTemperature())
                .build();
    }

}